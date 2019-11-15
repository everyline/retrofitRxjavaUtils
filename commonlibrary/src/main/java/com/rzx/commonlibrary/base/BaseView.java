package com.rzx.commonlibrary.base;

/**
 * 文件描述：
 * 作者：G
 * 创建时间：2019/8/10
 */
public interface BaseView {
    void showLoading();

    void dismissLoading();

    void showError(Throwable throwable);
}
