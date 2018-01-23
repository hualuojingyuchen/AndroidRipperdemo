package com.example.dongfang.androidripperdemo.presenterlayer;

import com.example.dongfang.androidripperdemo.viewlayer.ChangeViewLayer;
import com.meituan.android.hplus.ripper.block.IBlock;
import com.meituan.android.hplus.ripper.presenter.IPresenterLayer;
import com.meituan.android.hplus.ripper.view.IViewLayer;

/**
 * @package：com.example.dongfang.androidripperdemo
 * @fileName：ChangePresenterLayer
 * @author：dongfang
 * @date：2018/1/22s
 * @describe TODO
 */

public class ChangePresenterLayer implements IPresenterLayer {
    private IBlock block;
    private ChangeViewLayer viewLayer;
    private boolean show;

    @Override
    public void onBindBlock(IBlock block) {
        this.block = block;
        viewLayer = (ChangeViewLayer) block.getViewLayer();
        viewLayer.setLayer(this);
    }

    @Override
    public IViewLayer getIViewLayer() {
        return viewLayer;
    }


    public void btnClick() {
        show = !show;
        block.getWhiteBoard().notify("random",true);
    }

    public void btnClickClear() {

        block.getWhiteBoard().notify("clear",true);
    }
}
