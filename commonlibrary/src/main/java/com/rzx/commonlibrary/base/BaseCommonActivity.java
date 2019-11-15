package com.rzx.commonlibrary.base;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

import com.rzx.commonlibrary.utils.JActivityManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 文件描述：
 * 作者：G
 * 创建时间：2019/8/1
 */
public abstract class BaseCommonActivity extends RxAppCompatActivity {
    private Unbinder mUnBinder;
    final RxPermissions rxPermissions = new RxPermissions(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnBinder = ButterKnife.bind(this);

        JActivityManager.getInstance().addActivity(this);

    }


    abstract public int getLayoutId();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();

        JActivityManager.getInstance().closeActivity(this);
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
