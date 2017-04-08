package com.darongshu.login;

import android.util.Log;

/**
 * Created by Administrator on 2016/3/15.
 */
public class LogUtil {

    public static final boolean isDebug=true;
    public static final String tag="LogUtil";

    public static void d(String tag,String msg){
        if (isDebug){
            Log.d(tag,msg);
        }

    }
    public static void d(String msg){
        if (isDebug){
            Log.d(tag,msg);
        }

    }
    public static void e(String tag,String msg){
        if (isDebug){
            Log.d(tag,msg);
        }

    }
    public static void e(String msg){
        if (isDebug){
            Log.d(tag,msg);
        }

    }



}
