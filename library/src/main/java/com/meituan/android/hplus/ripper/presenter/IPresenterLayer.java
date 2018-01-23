package com.meituan.android.hplus.ripper.presenter;

import com.meituan.android.hplus.ripper.block.IBlock;
import com.meituan.android.hplus.ripper.view.IViewLayer;

/**
 * Created by huzhaoxu on 2016/12/2.
 */

public interface IPresenterLayer {

    void onBindBlock(IBlock block);

    IViewLayer getIViewLayer();
}
