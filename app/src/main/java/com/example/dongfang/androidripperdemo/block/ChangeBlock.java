package com.example.dongfang.androidripperdemo.block;

import com.example.dongfang.androidripperdemo.presenterlayer.ChangePresenterLayer;
import com.example.dongfang.androidripperdemo.viewlayer.ChangeViewLayer;
import com.example.dongfang.androidripperdemo.DefaultBlock;
import com.meituan.android.hplus.ripper.model.WhiteBoard;

/**
 * @package：com.example.dongfang.androidripperdemo
 * @fileName：ChangeBlock
 * @author：dongfang
 * @date：2018/1/22
 * @describe TODO
 */

public class ChangeBlock extends DefaultBlock {


    public ChangeBlock(WhiteBoard whiteBoard) {
        super(whiteBoard);
        viewLayer = new ChangeViewLayer();
        presenterLayer = new ChangePresenterLayer();
        init();

    }
}
