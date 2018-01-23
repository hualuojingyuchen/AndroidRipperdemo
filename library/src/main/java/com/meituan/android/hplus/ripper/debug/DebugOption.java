package com.meituan.android.hplus.ripper.debug;

/**
 * Created by huzhaoxu on 2017/2/3.
 */

public final class DebugOption {

    private DebugOption(){

    }

    private static boolean debug = false;

    public static void enableDebug(){
        debug = true;
    }

    public static void disableDebug(){
        debug = false;
    }

    public static boolean isDebug(){
        return debug;
    }
}
