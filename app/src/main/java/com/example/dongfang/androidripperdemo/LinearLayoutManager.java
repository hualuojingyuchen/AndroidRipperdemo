package com.example.dongfang.androidripperdemo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.meituan.android.hplus.ripper.block.BlockManager;
import com.meituan.android.hplus.ripper.block.IBlock;
import com.meituan.android.hplus.ripper.layout.ILayoutManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by huzhaoxu on 2016/12/5.
 */
public class LinearLayoutManager implements ILayoutManager {

    private LinearLayout container;

    private Map<IBlock, View> cachedViewMap;


    private BlockManager manager;

    public LinearLayoutManager() {
        cachedViewMap = new HashMap<>();
    }

    @Override
    public void setManager(BlockManager manager) {
        this.manager = manager;
    }

    @Override
    public void onCreateView(BlockManager manager) {
        List<IBlock> blockList = manager.getBlocks();
        for (int i = 0, size = blockList.size(); i < size; i++) {
            createViewInternal(blockList, blockList.get(i));
        }
    }

    @Override
    public void onCreateView() {
        List<IBlock> blockList = manager.getBlocks();
        for (int i = 0, size = blockList.size(); i < size; i++) {
            createViewInternal(blockList, blockList.get(i));
        }
    }

    @Override
    public void onCreateView(BlockManager manager, IBlock block) {
        List<IBlock> blockList = manager.getBlocks();
        if (!blockList.contains(block)) {
            return;
        }
        createViewInternal(blockList, block);
    }

    @Override
    public void onCreateView(IBlock block) {
        List<IBlock> blockList = manager.getBlocks();
        if (!blockList.contains(block)) {
            return;
        }
        createViewInternal(blockList, block);
    }


    @Override
    public void onUpdateView(BlockManager manager) {
        List<IBlock> blockList = manager.getConcernBlock();
        for (int i = 0, size = blockList.size(); i < size; i++) {
            IBlock currentBlock = blockList.get(i);
            updateViewInternal(blockList, currentBlock);
        }
    }

    @Override
    public void onUpdateView() {
        List<IBlock> blockList = manager.getConcernBlock();
        for (int i = 0, size = blockList.size(); i < size; i++) {
            IBlock currentBlock = blockList.get(i);
            updateViewInternal(blockList, currentBlock);
        }
    }

    @Override
    public void onUpdateView(BlockManager manager, IBlock block) {
        List<IBlock> blockList = manager.getConcernBlock();
        if (!blockList.contains(block)) {
            return;
        }
        updateViewInternal(blockList, block);
    }

    @Override
    public void onUpdateView(IBlock block) {
        List<IBlock> blockList = manager.getConcernBlock();
        if (!blockList.contains(block)) {
            return;
        }
        updateViewInternal(blockList, block);
    }

    private void createViewInternal(List<IBlock> blockList, IBlock currentBlock) {
        if (!currentBlock.getViewLayer().isAvailable()) {
            return; //view认为自己不需要被初始化，不添加
        }
        View v = currentBlock.getViewLayer().onCreateView(null, container);
        int positionIndex = 0;
        for (IBlock block : blockList) {
            if (block == currentBlock) {
                break;
            }
            if (block.getViewLayer().isAvailable()) {
                positionIndex++;
            }
        }
        if (positionIndex < container.getChildCount()) {
            container.addView(v, positionIndex);
        } else {
            container.addView(v);
        }
        currentBlock.getViewLayer().onUpdateView(v, null, container);
        cachedViewMap.put(currentBlock, v);
    }

    private void updateViewInternal(List<IBlock> blockList, IBlock currentBlock) {
        if (cachedViewMap.get(currentBlock) == null) {
            createViewInternal(blockList, currentBlock);
            currentBlock.getViewLayer().onUpdateView(cachedViewMap.get(currentBlock), null, container);
        } else {
            View cachedView = cachedViewMap.get(currentBlock);
            if (currentBlock.getViewLayer().useRecycledView()) {
                currentBlock.getViewLayer().onUpdateView(cachedView, null, container);
            } else {
                int viewIndex = container.indexOfChild(cachedView);
                View v = currentBlock.getViewLayer().onCreateView(null, container);
                container.removeView(cachedView);
                container.addView(v, viewIndex);
                currentBlock.getViewLayer().onUpdateView(v, null, container);
                cachedViewMap.put(currentBlock, v);
            }
        }
    }

    @Override
    public void setContainer(ViewGroup container) {
        if (!(container instanceof LinearLayout)) {
            throw new IllegalArgumentException("container need LinearLayout");
        }
        this.container = (LinearLayout) container;
    }

    @Override
    public ViewGroup getContainer() {
        return container;
    }
}
