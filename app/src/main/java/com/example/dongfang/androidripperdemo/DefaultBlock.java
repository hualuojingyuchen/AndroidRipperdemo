package com.example.dongfang.androidripperdemo;

import com.meituan.android.hplus.ripper.block.BlockManager;
import com.meituan.android.hplus.ripper.block.LifecycleBlock;
import com.meituan.android.hplus.ripper.model.WhiteBoard;
import com.meituan.android.hplus.ripper.presenter.IPresenterLayer;
import com.meituan.android.hplus.ripper.view.IViewLayer;

/**
 * Created by xudongfang on 2018/1/22.
 */

public class DefaultBlock extends LifecycleBlock {

    protected IViewLayer viewLayer;
    protected IPresenterLayer presenterLayer;
    protected WhiteBoard mWhiteBoard;
    protected BlockManager mBlockManager;

    public DefaultBlock(WhiteBoard whiteBoard){
        mWhiteBoard = whiteBoard;
    }

    public void init(){
        presenterLayer.onBindBlock(this);
    }

    @Override
    public IViewLayer getViewLayer() {
        return viewLayer;
    }

    @Override
    public IPresenterLayer getPresenterLayer() {
        return presenterLayer;
    }

    @Override
    public WhiteBoard getWhiteBoard() {
        return mWhiteBoard;
    }

    @Override
    public void onAttachBlockManager(BlockManager manager) {
        mBlockManager = manager;
    }

    @Override
    public BlockManager getBlockManager() {
        return mBlockManager;
    }
}
