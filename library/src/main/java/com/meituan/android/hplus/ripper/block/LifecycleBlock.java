package com.meituan.android.hplus.ripper.block;

import android.os.Bundle;
import android.util.Pair;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.RxLifecycle;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subjects.BehaviorSubject;

/**
 * Created by huzhaoxu on 2017/1/3.
 */

public abstract class LifecycleBlock implements IBlock, IAvoidStateLoss {

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    public void onStart() {
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
    }

    Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    public <T> Observable.Transformer<T, T> avoidStateLoss() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> source) {
                final Observable<FragmentEvent> sharedLifecycle = lifecycle().share();
                return source.observeOn(AndroidSchedulers.mainThread()).withLatestFrom(sharedLifecycle, new Func2<T, FragmentEvent, Pair<T, FragmentEvent>>() {
                    @Override
                    public Pair<T, FragmentEvent> call(T first, FragmentEvent second) {
                        return new Pair<T, FragmentEvent>(first, second);
                    }
                }).delay(new Func1<Pair<T, FragmentEvent>, Observable<FragmentEvent>>() {
                    @Override
                    public Observable<FragmentEvent> call(Pair<T, FragmentEvent> p) {
                        if (p.second.compareTo(FragmentEvent.START) >= 0 && p.second.compareTo(FragmentEvent.STOP) < 0) {
                            return Observable.just(FragmentEvent.START);
                        } else {
                            return sharedLifecycle.filter(new Func1<FragmentEvent, Boolean>() {
                                @Override
                                public Boolean call(FragmentEvent e) {
                                    return e == FragmentEvent.START;
                                }
                            }).take(1);
                        }
                    }
                }).map(new Func1<Pair<T, FragmentEvent>, T>() {
                    @Override
                    public T call(Pair<T, FragmentEvent> p) {
                        return p.first;
                    }
                }).<T>compose((Observable.Transformer<? super T, ? extends T>) bindUntilEvent(FragmentEvent.DESTROY));
            }
        };
    }

    <T> Observable.Transformer<T, T> bindToLifecycle() {
        return RxLifecycle.bindFragment(lifecycleSubject);
    }

    <T> Observable.Transformer<T, T> bindUntilEvent(FragmentEvent event) {
        return RxLifecycle.bindUntilFragmentEvent(lifecycleSubject, event);
    }
}
