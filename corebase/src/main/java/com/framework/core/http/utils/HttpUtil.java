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

package com.framework.core.http.utils;

import android.os.Looper;

import com.framework.core.http.func.HandleFuc;
import com.framework.core.http.func.HttpResponseFunc;
import com.framework.core.http.model.ApiResult;
import com.framework.core.log.PrintLog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * <p>描述：网络框架使用的工具类</p>
 * 作者： zhouyou<br>
 * 日期： 2017/5/15 17:13 <br>
 * 版本： v1.0<br>
 */
public class HttpUtil {
    public static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * 根据参数和baseUrl得到最终的Url
     * @param url
     * @param params
     * @return
     */
    public static String createUrlFromParams(String url, Map<String, String> params) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if (url.indexOf('&') > 0 || url.indexOf('?') > 0) sb.append("&");
            else sb.append("?");
            for (Map.Entry<String, String> urlParams : params.entrySet()) {
                String urlValues = urlParams.getValue();
                //对参数进行 utf-8 编码,防止头信息传中文
                //String urlValue = URLEncoder.encode(urlValues, UTF8.name());
                sb.append(urlParams.getKey()).append("=").append(urlValues).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (Exception e) {
            PrintLog.e(e.getMessage());
        }
        return url;
    }

    /**
     * 异步切换，且提取出ApiResult中的T
     */
    public static <T> ObservableTransformer<ApiResult<T>, T> _io_main() {
        return new ObservableTransformer<ApiResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<ApiResult<T>> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new HandleFuc<T>())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                PrintLog.i("+++doOnSubscribe+++" + disposable.isDisposed());
                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                PrintLog.i("+++doFinally+++");
                            }
                        })
                        .onErrorResumeNext(new HttpResponseFunc<T>());
            }
        };
    }


    /**
     * 只是提取T，没有进行线程切换，同步时使用
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<ApiResult<T>, T> _main() {
        return new ObservableTransformer<ApiResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<ApiResult<T>> upstream) {
                return upstream
                        .map(new HandleFuc<T>())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                PrintLog.i("+++doOnSubscribe+++" + disposable.isDisposed());
                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                PrintLog.i("+++doFinally+++");
                            }
                        })
                        .onErrorResumeNext(new HttpResponseFunc<T>());
            }
        };
    }

    public static <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }

    public static boolean checkMain() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static RequestBody createJson(String jsonString) {
        checkNotNull(jsonString, "json not null!");
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonString);
    }

    /**
     * @param name 文件名
     * @return 创建文件请求体
     */
    public static RequestBody createFile(String name) {
        checkNotNull(name, "name not null!");
        return RequestBody.create(okhttp3.MediaType.parse("multipart/form-data; charset=utf-8"), name);
    }

    /**
     * @param file 文件对象
     * @return 创建文件请求体
     */
    public static RequestBody createFile(File file) {
        checkNotNull(file, "file not null!");
        return RequestBody.create(okhttp3.MediaType.parse("multipart/form-data; charset=utf-8"), file);
    }

    /**
     * @param file 图片文件
     * @return 创建文件请求体
     */
    public static RequestBody createImage(File file) {
        checkNotNull(file, "file not null!");
        return RequestBody.create(okhttp3.MediaType.parse("image/jpg; charset=utf-8"), file);
    }

    /**
     * 对RequestBody.create的一个拓展，支持InputStream参数
     */
    public static RequestBody create(final MediaType mediaType, final InputStream inputStream) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public long contentLength() {
                try {
                    return inputStream.available();
                } catch (IOException e) {
                    return 0;
                }
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }
}
