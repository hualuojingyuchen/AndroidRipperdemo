package com.meituan.android.hplus.ripper.layout;

import android.view.ViewGroup;

import com.meituan.android.hplus.ripper.block.BlockManager;
import com.meituan.android.hplus.ripper.block.IBlock;

/**
 * Created by huzhaoxu on 2016/12/4.
 */
public interface ILayoutManager {

    @Deprecated
    void onCreateView(BlockManager manager);

    void onCreateView();

    @Deprecated
    void onCreateView(BlockManager manager, IBlock block);

    void onCreateView(IBlock block);

    @Deprecated
    void onUpdateView(BlockManager manager);

    void onUpdateView();

    @Deprecated
    void onUpdateView(BlockManager manager, IBlock block);

    void onUpdateView(IBlock block);

    void setContainer(ViewGroup container);

    ViewGroup getContainer();

    void setManager(BlockManager manager);

}
