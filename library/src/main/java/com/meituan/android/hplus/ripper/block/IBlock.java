package com.meituan.android.hplus.ripper.block;

import android.os.Bundle;

import com.meituan.android.hplus.ripper.model.WhiteBoard;
import com.meituan.android.hplus.ripper.presenter.IPresenterLayer;
import com.meituan.android.hplus.ripper.view.IViewLayer;

import rx.Observable;

/**
 * Created by huzhaoxu on 2016/12/2.
 */

public interface IBlock extends IAvoidStateLoss{

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    IViewLayer getViewLayer();

    IPresenterLayer getPresenterLayer();

    <T> Observable.Transformer<T, T> avoidStateLoss();

    WhiteBoard getWhiteBoard();

    void onAttachBlockManager(BlockManager manager);

    BlockManager getBlockManager();
}
