package com.example.dongfang.androidripperdemo.block;

import com.example.dongfang.androidripperdemo.DefaultBlock;
import com.example.dongfang.androidripperdemo.presenterlayer.InputViewPresenter;
import com.example.dongfang.androidripperdemo.viewlayer.InputViewlayer;
import com.meituan.android.hplus.ripper.model.WhiteBoard;

/**
 * @package：com.example.dongfang.androidripperdemo.block
 * @fileName：InputBlock
 * @author：dongfang
 * @date：2018/1/22
 * @describe TODO
 */

public class InputBlock extends DefaultBlock {
    public InputBlock(WhiteBoard whiteBoard) {
        super(whiteBoard);
        viewLayer = new InputViewlayer();
        presenterLayer= new InputViewPresenter();
        init();
    }
}
