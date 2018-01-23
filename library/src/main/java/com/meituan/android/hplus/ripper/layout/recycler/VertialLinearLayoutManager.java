package com.meituan.android.hplus.ripper.layout.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.meituan.android.hplus.ripper.block.BlockManager;
import com.meituan.android.hplus.ripper.block.IBlock;
import com.meituan.android.hplus.ripper.layout.ILayoutManager;

/**
 * Created by huzhaoxu on 2017/2/10.
 */

public class VertialLinearLayoutManager implements ILayoutManager {

    private RecyclerView container;
    private BlockManager manager;


    @Override
    public void onCreateView(BlockManager manager) {
        this.manager = manager;

    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void onCreateView(BlockManager manager, IBlock block) {

    }

    @Override
    public void onCreateView(IBlock block) {

    }

    @Override
    public void onUpdateView(BlockManager manager) {

    }

    @Override
    public void onUpdateView() {

    }

    @Override
    public void onUpdateView(BlockManager manager, IBlock block) {

    }

    @Override
    public void onUpdateView(IBlock block) {

    }

    @Override
    public void setContainer(ViewGroup container) {
        if(!(container instanceof RecyclerView)){
            throw new IllegalArgumentException("container need RecyclerView");
        }
    }

    @Override
    public ViewGroup getContainer() {
        return container;
    }

    @Override
    public void setManager(BlockManager manager) {
        this.manager = manager;
    }


}
