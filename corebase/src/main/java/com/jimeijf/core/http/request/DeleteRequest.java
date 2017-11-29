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

package com.jimeijf.core.http.request;

import com.jimeijf.core.cache.model.CacheResult;
import com.jimeijf.core.http.callback.CallBack;
import com.jimeijf.core.http.callback.CallBackProxy;
import com.jimeijf.core.http.func.CacheResultFunc;
import com.jimeijf.core.http.model.ApiResult;
import com.jimeijf.core.http.subsciber.CallBackSubsciber;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * <p>描述：通用的删除请求</p>
 * 作者： zhouyou<br>
 * 日期： 2017/4/28 15:02 <br>
 * 版本： v1.0<br>
 */
public class DeleteRequest extends BaseRequest<DeleteRequest> {
    public DeleteRequest(String url) {
        super(url);
    }

    public <T> Disposable execute(CallBack<T> callBack) {
        return execute(new CallBackProxy<ApiResult<T>, T>(callBack) {
        });
    }

    public <T> Disposable execute(CallBackProxy<? extends ApiResult<T>, T> proxy) {
        Observable<CacheResult<T>> observable = build().toObservable(generateRequest(), proxy);
        if (CacheResult.class != proxy.getCallBack().getRawType()) {
            return observable.compose(new ObservableTransformer<CacheResult<T>, T>() {
                @Override
                public ObservableSource<T> apply(@NonNull Observable<CacheResult<T>> upstream) {
                    return upstream.map(new CacheResultFunc<T>());
                }
            }).subscribeWith(new CallBackSubsciber<T>(context, proxy.getCallBack()));
        } else {
            return observable.subscribeWith(new CallBackSubsciber<CacheResult<T>>(context, proxy.getCallBack()));
        }
    }


    @Override
    protected Observable<ResponseBody> generateRequest() {
        return apiManager.delete(url, params.urlParamsMap);
    }
}
