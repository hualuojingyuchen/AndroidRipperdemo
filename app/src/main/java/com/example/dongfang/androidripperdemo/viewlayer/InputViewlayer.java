package com.example.dongfang.androidripperdemo.viewlayer;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.dongfang.androidripperdemo.Info;
import com.example.dongfang.androidripperdemo.R;
import com.example.dongfang.androidripperdemo.presenterlayer.InputViewPresenter;
import com.meituan.android.hplus.ripper.view.IViewLayer;

/**
 * @package：com.example.dongfang.androidripperdemo.viewlayer
 * @fileName：InputViewPlayer
 * @author：dongfang
 * @date：2018/1/22
 * @describe TODO
 */

public class InputViewlayer implements IViewLayer {
    private  Info info;
    private InputViewPresenter layer;
    public EditText et_left;
    public EditText et_right;
    public InputViewlayer(){
        info = new Info();
    }
    @Override
    public View onCreateView(Bundle scenario, ViewGroup parent) {
        Context context = parent.getContext();
        final View input = LayoutInflater.from(context).inflate(R.layout.input, null);
        et_left = input.findViewById(R.id.et_left);
        et_right = input.findViewById(R.id.et_right);
        et_left.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    if (!TextUtils.isEmpty(editable)){
                        info.left = Integer.valueOf(editable.toString());
                        layer.updateBodyInfo(info);
                    }

            }
        });
        et_right.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)){
                    info.right = Integer.valueOf(editable.toString());
                    layer.updateBodyInfo(info);
                }
            }
        });



        return input;
    }

    @Override
    public void onUpdateView(View view, Bundle scenario, ViewGroup parent) {

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

    public void setLayer(InputViewPresenter layer) {
        this.layer = layer;
    }
}
