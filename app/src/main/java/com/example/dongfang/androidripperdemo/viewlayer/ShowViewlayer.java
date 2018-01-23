package com.example.dongfang.androidripperdemo.viewlayer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dongfang.androidripperdemo.R;
import com.example.dongfang.androidripperdemo.presenterlayer.ShowPresenter;
import com.meituan.android.hplus.ripper.view.IViewLayer;

/**
 * @package：com.example.dongfang.androidripperdemo.viewlayer
 * @fileName：ShowViewlayer
 * @author：dongfang
 * @date：2018/1/22
 * @describe TODO
 */

public class ShowViewlayer implements IViewLayer {
    private ShowPresenter layer;
    private long result = 0;

    @Override
    public View onCreateView(Bundle scenario, ViewGroup parent) {
        Context context = parent.getContext();
        View showview = LayoutInflater.from(context).inflate(R.layout.showviewlayer, null);
        TextView tv_show = showview.findViewById(R.id.tv_show);
        return showview;
    }

    @Override
    public void onUpdateView(View view, Bundle scenario, ViewGroup parent) {
        TextView tv_show = view.findViewById(R.id.tv_show);
//        scenario.get
        tv_show.setText(result+"");
    }

    @Override
    public boolean useRecycledView() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public int getVisibility() {
        return View.VISIBLE;
    }

    public void setLayer(ShowPresenter layer) {
        this.layer = layer;
    }

    public void setResult(Long result) {
        this.result = result;
    }
}
