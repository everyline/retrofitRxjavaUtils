package com.rzx.commonlibrary.base;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.KeyboardUtils;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.jaeger.library.StatusBarUtil;
import com.rzx.commonlibrary.R;
import com.rzx.commonlibrary.utils.JActivityManager;
import com.rzx.commonlibrary.view.MultipleStatusView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 文件描述：
 * 作者：G
 * 创建时间：2019/8/15
 */
abstract public class BaseCommonMvpActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    public T mPresenter;
    protected MultipleStatusView mLayoutStatusView;
    private Unbinder mUnBinder;
    public final RxPermissions rxPermissions = new RxPermissions(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnBinder = ButterKnife.bind(this);

        JActivityManager.getInstance().addActivity(this);


        getTitleBar();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
        initView();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initData();
        initListener();
        initRetryClickListener();   //多种状态切换的view 重试点击事件
        retryStart();


        if (getTitleBar() != 0) {
            TitleBar titleBar = findViewById(getTitleBar());
            titleBar.setOnTitleBarListener(new OnTitleBarListener() {
                @Override
                public void onLeftClick(View v) {
                    finish();
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

    protected void initRetryClickListener() {
        if (mLayoutStatusView != null) {
            mLayoutStatusView.setOnRetryClickListener(new RetryClickListener());
        }
    }


    public class RetryClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            retryStart();
        }
    }


    abstract public int getLayoutId();

    protected abstract int getTitleBar();

    public abstract void initView();

    public abstract void initData();

    public abstract void initListener();

    protected abstract void retryStart();

    protected abstract void onRightClickListener(View v);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mUnBinder.unbind();
        KeyboardUtils.hideSoftInput(this);
        JActivityManager.getInstance().closeActivity(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus() && event.getAction() == MotionEvent.ACTION_UP) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}
