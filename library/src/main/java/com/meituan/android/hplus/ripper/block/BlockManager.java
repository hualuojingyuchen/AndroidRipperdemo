package com.meituan.android.hplus.ripper.block;

import android.os.Bundle;

import com.meituan.android.hplus.ripper.debug.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by huzhaoxu on 2016/12/5.
 */
public class BlockManager {

    private List<IBlock> blockList;

    private Map<String, Set<IBlock>> changeMap;

    private Set<String> concernKeys;


    public BlockManager() {
        blockList = new ArrayList<>();
        changeMap = new HashMap<>();
        concernKeys = new HashSet<>();
    }

    public void addBlockConcern(IBlock block, String concern) {
        if (!blockList.contains(block)) {
            return;
        }
        if (changeMap.get(concern) == null) {
            changeMap.put(concern, new HashSet<IBlock>());
        }
        changeMap.get(concern).add(block);
    }

    public void addConcernKeys(String key) {
        concernKeys.add(key);
    }


    public List<IBlock> getConcernBlock() {
        List<IBlock> result = new LinkedList<>();
        for (String key : concernKeys) {
            Set<IBlock> set = changeMap.get(key);
            if (set != null) {
                result.addAll(set);
            }
        }
        if (result.size() == 0) {
            Logger.i("no concern block, update all");
            return blockList;
        }
        changeMap.clear();
        Logger.i("concern block", result);
        return result;
    }


    public void addBlock(IBlock block) {
        block.onAttachBlockManager(this);
        blockList.add(block);
    }

    public void setBlockList(List<IBlock> blockList) {
        this.blockList.clear();
        for (IBlock block : blockList) {
            block.onAttachBlockManager(this);
        }
        this.blockList.addAll(blockList);
    }

    public List getBlocks() {
        return blockList;
    }

    public void performCreateBlock(Bundle savedInstanceState) {
        for (IBlock block : blockList) {
            block.onCreate(savedInstanceState);
        }
    }

    public void performStartBlock() {
        for (IBlock block : blockList) {
            block.onStart();
        }
    }

    public void performResumeBlock() {
        for (IBlock block : blockList) {
            block.onResume();
        }
    }

    public void performPauseBlock() {
        for (IBlock block : blockList) {
            block.onPause();
        }
    }

    public void performStopBlock() {
        for (IBlock block : blockList) {
            block.onStop();
        }
    }

    public void performDestroyBlock() {
        for (IBlock block : blockList) {
            block.onDestroy();
        }
    }

}

