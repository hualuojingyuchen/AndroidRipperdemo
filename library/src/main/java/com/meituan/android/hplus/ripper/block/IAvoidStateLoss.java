package com.meituan.android.hplus.ripper.block;

import rx.Observable;

/**
 * Created by huzhaoxu on 2017/1/5.
 */

public interface IAvoidStateLoss {
    <T> Observable.Transformer<T, T> avoidStateLoss();
}
