package com.rzx.commonlibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.jaeger.library.StatusBarUtil;
import com.rzx.commonlibrary.R;
import com.rzx.commonlibrary.http.ApiException;
import com.rzx.commonlibrary.view.MultipleStatusView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 文件描述：
 * 作者：G
 * 创建时间：2019/8/1
 */
abstract public class BaseCommonFragment<T extends BasePresenter> extends Fragment implements BaseView {
    /**
     * 视图是否加载完毕
     */
    private boolean isViewPrepare = false;
    /**
     * 数据是否加载过了
     */
    private boolean hasLoadData = false;
    public RxPermissions rxPermissions;

    protected View mView;
    protected Context mContext;
    public Activity activity;
    private Unbinder mUnBinder;
    protected T mPresenter;
    protected MultipleStatusView mLayoutStatusView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        activity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        isViewPrepare = true;
        rxPermissions = new RxPermissions(this);
        StatusBarUtil.setColor(activity, getResources().getColor(R.color.white), 0);
        initView();
        getTitleBar();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initData();
        lazyLoadDataIfPrepared();
        initListener();
        initRetryClickListener();   //多种状态切换的view 重试点击事件


        if (getTitleBar() != 0) {
            TitleBar titleBar = view.findViewById(getTitleBar());
            titleBar.setOnTitleBarListener(new OnTitleBarListener() {
                @Override
                public void onLeftClick(View v) {

                }

                @Override
                public void onTitleClick(View v) {

                }

                @Override
                public void onRightClick(View v) {
                    onRightClickListener(v);
                }
            });
        }

    }

    private void initRetryClickListener() {
        if (mLayoutStatusView != null) {
            mLayoutStatusView.setOnRetryClickListener(new RetryClickListener());
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }


    public class RetryClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            lazyLoad();
        }
    }

    public void handleThrowable(Throwable throwable) {
        throwable.printStackTrace();

        //todo  比如token 失效 或者封停用户 直接跳到 登录页   其他情况  可以在这里做统一处理
        if (throwable instanceof ApiException) {
            ApiException exception = (ApiException) throwable;
            String defaultErrorMsg = exception.getDisplayMessage();
            int err_code = exception.getCode();
            LogUtils.d("err_code===", err_code);

            if (!TextUtils.isEmpty(defaultErrorMsg)) {
                ToastUtils.showShort(defaultErrorMsg);
            }

        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            lazyLoadDataIfPrepared();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mUnBinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }


    abstract public int getLayoutId();

    protected abstract int getTitleBar();

    abstract public void initView();
    abstract public void initData();

    abstract public void lazyLoad();

    public abstract void initListener();

    protected abstract void onRightClickListener(View v);

    public void lazyLoadDataIfPrepared() {
        if (getUserVisibleHint() && isViewPrepare && !hasLoadData) {
            lazyLoad();
            hasLoadData = true;
        }
    }
}
