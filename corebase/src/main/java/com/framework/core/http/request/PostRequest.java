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

package com.framework.core.http.request;



import com.framework.core.cache.model.CacheResult;
import com.framework.core.http.callback.CallBack;
import com.framework.core.http.callback.CallBackProxy;
import com.framework.core.http.callback.CallClazzProxy;
import com.framework.core.http.func.ApiResultFunc;
import com.framework.core.http.func.CacheResultFunc;
import com.framework.core.http.func.RetryExceptionFunc;
import com.framework.core.http.model.ApiResult;
import com.framework.core.http.subsciber.CallBackSubsciber;
import com.framework.core.http.utils.HttpUtil;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * <p>描述：继承自BaseBodyRequest的post请求</p>
 * 作者： zhouyou<br>
 * 日期： 2017/4/28 14:29 <br>
 * 版本： v1.0<br>
 */
public class PostRequest extends BaseBodyRequest<PostRequest> {
    public PostRequest(String url) {
        super(url);
    }

    public <T> Observable<T> execute(Class<T> clazz) {
        return execute(new CallClazzProxy<ApiResult<T>, T>(clazz) {
        });
    }


    public <T> Observable<T> execute(Type type) {
        return execute(new CallClazzProxy<ApiResult<T>, T>(type) {
        });
    }

    public <T> Observable<T> execute(CallClazzProxy<? extends ApiResult<T>, T> proxy) {
        if(paramConvert != null){
            changeParams2JsonStr();
        }
        return build().generateRequest()
                .map(new ApiResultFunc(proxy.getType(),context))
                .compose(isSyncRequest ? HttpUtil._main() : HttpUtil._io_main())
                .compose(rxCache.transformer(cacheMode, proxy.getCallType()))
                .retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay))
                .compose(new ObservableTransformer() {
                    @Override
                    public ObservableSource apply(@NonNull Observable upstream) {
                        return upstream.map(new CacheResultFunc<T>());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <T> Disposable execute(CallBack<T> callBack) {
        return execute(new CallBackProxy<ApiResult<T>, T>(callBack) {
        });
    }

    public <T> Disposable execute(CallBackProxy<? extends ApiResult<T>, T> proxy) {
        if(paramConvert != null){
            changeParams2JsonStr();
        }
        Observable<CacheResult<T>> observable = build().toObservable(generateRequest(), proxy);
        if (CacheResult.class != proxy.getCallBack().getRawType()) {
            return observable.compose(new ObservableTransformer<CacheResult<T>, T>() {
                @Override
                public ObservableSource<T> apply(@NonNull Observable<CacheResult<T>> upstream) {
                    return upstream.map(new CacheResultFunc<T>());
                }
            })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new CallBackSubsciber<T>(context, proxy.getCallBack()));
        } else {
            return observable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new CallBackSubsciber<CacheResult<T>>(context, proxy.getCallBack()));
        }
    }
}
