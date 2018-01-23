package com.meituan.android.hplus.ripper.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huzhaoxu on 2016/12/2.
 */

public interface IViewLayer {

    View onCreateView(Bundle scenario, ViewGroup parent);

    void onUpdateView(View view, Bundle scenario, ViewGroup parent);

    boolean useRecycledView();

    boolean isAvailable();

    int getVisibility();
}
