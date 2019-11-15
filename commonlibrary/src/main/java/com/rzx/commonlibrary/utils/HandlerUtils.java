package com.rzx.commonlibrary.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * 文件描述：
 * 作者：G
 * 创建时间：2019/9/2
 */
public class HandlerUtils {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static void runOnUiThread(Runnable runnable) {
        HANDLER.post(runnable);
    }

    public static void runOnUiThreadDelay(Runnable runnable, long delayMillis) {
        HANDLER.postDelayed(runnable, delayMillis);
    }

    public static void removeRunable(Runnable runnable) {
        HANDLER.removeCallbacks(runnable);
    }
}
