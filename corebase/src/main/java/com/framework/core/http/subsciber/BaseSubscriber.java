/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.framework.core.http.subsciber;

import android.content.Context;

import com.framework.core.base.BaseView;
import com.framework.core.http.exception.ApiException;
import com.framework.core.log.PrintLog;
import com.framework.core.util.NetWorkUtil;

import java.lang.ref.WeakReference;
import java.net.ConnectException;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * <p>描述：订阅的基类</p>
 * 1.可以防止内存泄露。<br>
 * 2.在onStart()没有网络时直接onCompleted();<br>
 * 3.统一处理了异常,所以在BaseRequest中就没必要再加统一处理异常了composite了<br>
 * 4.接入了异常处理机制，持有BaseView的弱引用。网络请求的异常统一交会UI层处理，如果不需要接入此机制
 *   可不传BaseView，然后重写onError即可
 * 作者： zhouyou<br>
 * 日期： 2016/12/20 10:35<br>
 * 版本： v2.0<br>
 */
public abstract class BaseSubscriber<T> extends DisposableObserver<T> {
    //context弱引用防止内存泄露
    private WeakReference<Context> contextWeakReference;
    //一般BaseView就是Activity，所以采用若引用防止内存溢出
    private WeakReference<BaseView> baseViewWeakReference;
    private String apiTag;

    public BaseSubscriber() {
    }

    /**
     * 传入Context 会自动走网络判断，无网络直接不发起请求
     * 但是这里如果要是需要缓存的时候不能传此参数
     * @param context
     */
    public BaseSubscriber(Context context) {
        if (context != null) {
            contextWeakReference = new WeakReference<>(context);
        }
    }

    public BaseSubscriber(BaseView baseView, String apiTag) {
        if (baseView != null) {
            baseViewWeakReference = new WeakReference<>(baseView);
        }
        this.apiTag = apiTag;
    }

    public BaseSubscriber(Context context,BaseView baseView, String apiTag) {
        if (context != null) {
            contextWeakReference = new WeakReference<>(context);
        }
        if (baseView != null) {
            baseViewWeakReference = new WeakReference<>(baseView);
        }
        this.apiTag = apiTag;
    }


    @Override
    protected void onStart() {
        PrintLog.e("-->http is onStart");
        if (contextWeakReference != null && contextWeakReference.get() != null && !NetWorkUtil.isNetworkAvailable(contextWeakReference.get())) {
            //Toast.makeText(context, "无网络，读取缓存数据", Toast.LENGTH_SHORT).show();
            ConnectException ex = new ConnectException("无网");
            onError(ex);
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        PrintLog.e("-->http is onNext");
        onResultSuccess(t);
    }

    protected abstract void onResultSuccess(T t);

    @Override
    public void onError(Throwable e) {
        //最终都以ApiException进行返回
        PrintLog.e("-->http is onError");
        if (e instanceof ApiException) {
            PrintLog.e("--> e instanceof ApiException err:" + e);
            //虽有的异常默认都会进行统一分发处理
            if (baseViewWeakReference != null && baseViewWeakReference.get() != null) {
                baseViewWeakReference.get().handleException(apiTag,(ApiException) e);
            }else{
                onError((ApiException) e);
            }
        } else {
            ApiException apiException = ApiException.handleException(e);
            PrintLog.e("--> e !instanceof ApiException err:" + e);
            if (baseViewWeakReference != null && baseViewWeakReference.get() != null) {
                baseViewWeakReference.get().handleException(apiTag,(ApiException) e);
            }else{
                onError(apiException);
            }
        }
    }

    @Override
    public void onComplete() {
        PrintLog.e("-->http is onComplete");
    }


    /**
     * 异常都已经分发处理，这个方法在指定了BaseView的情况下不需要单独处理
     * 如果有类需要完全自己处理异常，可以不传BaseView然后重写该方法处理
     * 也可以传BaseView然后在异常拦截器处理
     * 一般我们不需要重写这个方法进行处理通过构造器指定即可
     * @param e
     */
    public void onError(ApiException e){}

}
