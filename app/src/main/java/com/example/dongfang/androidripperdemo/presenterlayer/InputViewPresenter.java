package com.example.dongfang.androidripperdemo.presenterlayer;

import com.example.dongfang.androidripperdemo.Info;
import com.example.dongfang.androidripperdemo.viewlayer.InputViewlayer;
import com.meituan.android.hplus.ripper.block.IBlock;
import com.meituan.android.hplus.ripper.model.WhiteBoard;
import com.meituan.android.hplus.ripper.presenter.IPresenterLayer;
import com.meituan.android.hplus.ripper.view.IViewLayer;

import rx.functions.Action1;

/**
 * @package：com.example.dongfang.androidripperdemo.presenterlayer
 * @fileName：InputViewPresenter
 * @author：dongfang
 * @date：2018/1/22
 * @describe TODO
 */

public class InputViewPresenter implements IPresenterLayer {
    private IBlock block;
    private InputViewlayer viewLayer;
    private WhiteBoard whiteBoard;
    private Info info;

    @Override
    public void onBindBlock(final IBlock block) {
        this.block = block;
        viewLayer = (InputViewlayer) block.getViewLayer();
        viewLayer.setLayer(this);
        whiteBoard = block.getWhiteBoard();
        whiteBoard.getObservable("random",Boolean.class).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean){
                    viewLayer.et_left.setText((int)(1+Math.random()*10)+"");
                    viewLayer.et_right.setText((int)(1+Math.random()*10)+"");

                }
            }
        });
        whiteBoard.getObservable("clear",boolean.class).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean){
                    viewLayer.et_left.setText(0+"");
                    viewLayer.et_right.setText(0+"");
                }
            }
        });
    }

    /**
     * 得到初始化中的viewlayer
     * @return
     */
    @Override
    public IViewLayer getIViewLayer() {
        return viewLayer;
    }

    public void updateBodyInfo(Info info) {
        this.info = info;
        int right = info.right;
        int left = info.left;
        long num = right*left;

        whiteBoard.notify("result",num);

    }
}
