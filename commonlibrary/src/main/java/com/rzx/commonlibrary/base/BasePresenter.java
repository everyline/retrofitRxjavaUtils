package com.rzx.commonlibrary.base;

/**
 * 文件描述：
 * 作者：G
 * 创建时间：2019/8/10
 */
public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
