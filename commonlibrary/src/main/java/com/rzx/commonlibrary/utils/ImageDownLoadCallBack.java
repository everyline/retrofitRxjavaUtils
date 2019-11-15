package com.rzx.commonlibrary.utils;

import android.graphics.Bitmap;

import java.io.File;

/**
 * 文件描述：
 * 作者：G
 * 创建时间：2019/10/22
 */
public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(File file);

    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed();
}
