package com.meituan.android.hplus.ripper.util;

import android.util.Log;

import com.meituan.android.hplus.ripper.debug.DebugOption;

/**
 * Created by huzhaoxu on 2017/3/8.
 */

public final class Crash {

    private Crash(){

    }

    public static void crashAndExit(Throwable t){
        try {
            t.printStackTrace();
        } finally {
            if(DebugOption.isDebug()){
                System.exit(1);
            }
        }
    }
}
