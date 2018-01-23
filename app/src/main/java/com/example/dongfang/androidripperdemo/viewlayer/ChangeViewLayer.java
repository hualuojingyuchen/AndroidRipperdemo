package com.example.dongfang.androidripperdemo.viewlayer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dongfang.androidripperdemo.presenterlayer.ChangePresenterLayer;
import com.example.dongfang.androidripperdemo.R;
import com.meituan.android.hplus.ripper.view.IViewLayer;

/**
 * @package：com.example.dongfang.androidripperdemo
 * @fileName：ChangeViewLayer
 * @author：dongfang
 * @date：2018/1/22
 * @describe TODO
 */

public class ChangeViewLayer implements IViewLayer {


    private ChangePresenterLayer layer;
    //创建 & 绑定view
    @Override
    public View onCreateView(Bundle scenario, ViewGroup parent) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.change, null);
        View btn_click_me = v.findViewById(R.id.btn_click_me);
        View btn_click_me2 = v.findViewById(R.id.btn_click_me2);
        btn_click_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layer.btnClick();
            }
        });
        btn_click_me2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layer.btnClickClear();
            }
        });
        return v;
    }
    //更新view  可以拿到每个view对象  分别做更新
    @Override
    public void onUpdateView(View view, Bundle scenario, ViewGroup parent) {
        Toast.makeText(view.getContext() , "onUpdateView" , Toast.LENGTH_SHORT).show();
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

    public void setLayer(ChangePresenterLayer layer) {
        this.layer = layer;
    }
}
