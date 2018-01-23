package com.example.dongfang.androidripperdemo.presenterlayer;

import com.example.dongfang.androidripperdemo.viewlayer.ShowViewlayer;
import com.meituan.android.hplus.ripper.block.IBlock;
import com.meituan.android.hplus.ripper.model.WhiteBoard;
import com.meituan.android.hplus.ripper.presenter.IPresenterLayer;
import com.meituan.android.hplus.ripper.view.IViewLayer;

import rx.functions.Action1;

/**
 * @package：com.example.dongfang.androidripperdemo.presenterlayer
 * @fileName：ShowPresenter
 * @author：dongfang
 * @date：2018/1/22
 * @describe TODO
 */

public class ShowPresenter implements IPresenterLayer {
    private IBlock block;
    private ShowViewlayer viewLayer;

    @Override
    public void onBindBlock(IBlock block) {
        this.block = block;
        viewLayer = (ShowViewlayer) block.getViewLayer();
        viewLayer.setLayer(this);
        WhiteBoard whiteBoard = block.getWhiteBoard();
        whiteBoard.getObservable("result",long.class,block).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                if (viewLayer!=null){
                    viewLayer.setResult(aLong);
                }
            }
        });

    }

    @Override
    public IViewLayer getIViewLayer() {
        return viewLayer;
    }
}
