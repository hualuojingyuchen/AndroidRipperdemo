package com.example.dongfang.androidripperdemo.block;

import com.example.dongfang.androidripperdemo.DefaultBlock;
import com.example.dongfang.androidripperdemo.presenterlayer.ShowPresenter;
import com.example.dongfang.androidripperdemo.viewlayer.ShowViewlayer;
import com.meituan.android.hplus.ripper.model.WhiteBoard;

/**
 * @package：com.example.dongfang.androidripperdemo.block
 * @fileName：ShowBlock
 * @author：dongfang
 * @date：2018/1/22
 * @describe TODO
 */

public class ShowBlock extends DefaultBlock {

    public ShowBlock(WhiteBoard whiteBoard) {
        super(whiteBoard);
        viewLayer = new ShowViewlayer();
        presenterLayer = new ShowPresenter();
        init();
    }
}
