package com.meituan.android.hplus.ripper.helper;

import android.view.ViewGroup;

import com.meituan.android.hplus.ripper.block.BlockManager;
import com.meituan.android.hplus.ripper.block.IBlock;
import com.meituan.android.hplus.ripper.layout.ILayoutManager;
import com.meituan.android.hplus.ripper.model.WhiteBoard;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by huzhaoxu on 2017/1/3.
 */

public abstract class RipperWeaver {

    protected BlockManager blockManager;

    protected ILayoutManager layoutManager;

    protected WhiteBoard whiteBoard;

    protected ViewGroup container;

    public final void init(){
        blockManager = getBlockManager();
        layoutManager = getLayoutManager();
        whiteBoard = getWhiteBoard();
        container = getContainer();
        layoutManager.setContainer(container);
        blockManager.setBlockList(getBlockList());
        bindViewObserver();
    }

    private void bindViewObserver(){
        whiteBoard.getObservable(WhiteBoard.CREATE_VIEW_KEY, Object.class, null).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (layoutManager.getContainer() == null) {
                    layoutManager.setContainer(getContainer());
                }
                if (o == null) {
                    layoutManager.onCreateView(blockManager);
                }else if(o instanceof  IBlock){
                    IBlock targetBlock = (IBlock) o;
                    layoutManager.onCreateView(blockManager, targetBlock);
                }
            }
        });
        whiteBoard.getObservable(WhiteBoard.UPDATE_VIEW_KEY, Object.class, null).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (layoutManager.getContainer() == null) {
                    layoutManager.setContainer(getContainer());
                }
                if (o == null) {
                    layoutManager.onUpdateView(blockManager);
                } else if (o instanceof IBlock) {
                    IBlock targetBlock = (IBlock) o;
                    layoutManager.onUpdateView(blockManager, targetBlock);
                }
            }
        });
    }

    protected abstract BlockManager getBlockManager();

    protected abstract ILayoutManager getLayoutManager();

    protected abstract ViewGroup getContainer();

    protected abstract WhiteBoard getWhiteBoard();

    protected abstract List<IBlock> getBlockList();
}
