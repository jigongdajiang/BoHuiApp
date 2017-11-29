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

package com.jimeijf.core.http.subsciber;

import android.content.Context;

import com.jimeijf.core.http.exception.ApiException;
import com.jimeijf.core.log.PrintLog;
import com.jimeijf.core.util.NetWorkUtil;

import java.lang.ref.WeakReference;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * <p>描述：订阅的基类</p>
 * 1.可以防止内存泄露。<br>
 * 2.在onStart()没有网络时直接onCompleted();<br>
 * 3.统一处理了异常,所以在BaseRequest中就没必要再加统一处理异常了composite了<br>
 * 作者： zhouyou<br>
 * 日期： 2016/12/20 10:35<br>
 * 版本： v2.0<br>
 */
public abstract class BaseSubscriber<T> extends DisposableObserver<T> {
    //context弱引用防止内存泄露
    public WeakReference<Context> contextWeakReference;
    
    public BaseSubscriber() {
    }

    @Override
    protected void onStart() {
        PrintLog.e("-->http is onStart");
        if (contextWeakReference != null && contextWeakReference.get() != null && !NetWorkUtil.isNetworkAvailable(contextWeakReference.get())) {
            //Toast.makeText(context, "无网络，读取缓存数据", Toast.LENGTH_SHORT).show();
            onComplete();
        }
    }


    public BaseSubscriber(Context context) {
        if (context != null) {
            contextWeakReference = new WeakReference<>(context);
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        PrintLog.e("-->http is onNext");
    }

    @Override
    public final void onError(Throwable e) {
        //最终都以ApiException进行返回
        PrintLog.e("-->http is onError");
        if (e instanceof ApiException) {
            PrintLog.e("--> e instanceof ApiException err:" + e);
            onError((ApiException) e);
        } else {
            PrintLog.e("--> e !instanceof ApiException err:" + e);
            onError(ApiException.handleException(e));
        }
    }

    @Override
    public void onComplete() {
        PrintLog.e("-->http is onComplete");
    }


    public abstract void onError(ApiException e);

}
