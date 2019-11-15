package com.rzx.commonlibrary.http;

import com.blankj.utilcode.util.LogUtils;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * 文件描述：重试机制
 * 作者：G
 * 创建时间：2019/8/9
 */
public class RetryWhenDelay implements Function<Flowable<Throwable>, Publisher<?>> {
    private final static String TAG = "RetryWhenDelay=====";

    private int maxRetries = 3;
    private int retryDelayMillis = 3000;
    private int retryCount = 0;


    @Override
    public Publisher<?> apply(Flowable<Throwable> throwableFlowable) throws Exception {
        return throwableFlowable.flatMap(new Function<Throwable, Publisher<?>>() {
            @Override
            public Publisher<?> apply(Throwable throwable) throws Exception {
                LogUtils.dTag(TAG, "==进入重试机制===");
                if (throwable instanceof ApiException) { // todo  测试代码 需要修改
//                    if (((ApiException) throwable).getCode() == 100) {
//                        Disposable disposable= RetrofitUtils.getApiUrl()
//                                .getDemo()
//                                .compose(RxHelper.rxSchedulerHelper())
//                                .compose(RxHelper.handleMyResult())
//                                .subscribe(new Consumer<Demo>() {
//                                    @Override
//                                    public void accept(Demo demo) throws Exception {
//                                        LogUtils.dTag(TAG, "====重试成功=====");
//                                    }
//                                }, new Consumer<Throwable>() {
//                                    @Override
//                                    public void accept(Throwable throwable) throws Exception {
//                                        LogUtils.dTag(TAG, "=====重试失败====" + throwable.getMessage());
//                                    }
//                                });

//                    }


                    if (++retryCount <= maxRetries) {
                        return Flowable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                    } else {
                        return Flowable.error(throwable);
                    }
                }
                return null;
            }
        });
    }

}
