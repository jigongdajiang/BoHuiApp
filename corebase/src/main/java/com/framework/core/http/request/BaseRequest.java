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

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.framework.core.cache.RxCache;
import com.framework.core.cache.converter.IDiskConverter;
import com.framework.core.cache.model.CacheResult;
import com.framework.core.cache.stategy.CacheMode;
import com.framework.core.http.EasyHttp;
import com.framework.core.http.api.ApiService;
import com.framework.core.http.callback.CallBackProxy;
import com.framework.core.http.func.ApiResultFunc;
import com.framework.core.http.func.RetryExceptionFunc;
import com.framework.core.http.interceptor.BaseDynamicInterceptor;
import com.framework.core.http.interceptor.CacheInterceptor;
import com.framework.core.http.interceptor.CacheInterceptorOffline;
import com.framework.core.http.interceptor.HeadersInterceptor;
import com.framework.core.http.interceptor.NoCacheInterceptor;
import com.framework.core.http.model.ApiResult;
import com.framework.core.http.model.HttpHeaders;
import com.framework.core.http.model.HttpParams;
import com.framework.core.http.model.IParamConvert;
import com.framework.core.http.utils.HttpUtil;
import com.framework.core.http.utils.HttpsUtils;
import com.framework.core.log.PrintLog;
import com.framework.core.rxcore.RxTransform;

import java.io.File;
import java.io.InputStream;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * <p>描述：所有请求的基类</p>
 * 1.核心请求基类
 * 2.支持链式调用
 * 3.可以单独使用但是不建议单独使用
 * 4.与EasyHttp参数互通
 * 作者： zhouyou<br>
 * 日期： 2017/4/28 17:19 <br>
 * 版本： v1.0<br>
 */
public abstract class BaseRequest<R extends BaseRequest> {
    protected Cache cache = null;
    protected CacheMode cacheMode = CacheMode.NO_CACHE;   //默认无缓存
    protected long cacheTime = -1;                        //缓存时间
    protected String cacheKey;                            //缓存Key
    protected IDiskConverter diskConverter;               //设置Rxcache磁盘转换器
    protected String baseUrl;                             //BaseUrl
    protected String url;                                 //请求url
    protected long readTimeOut;                           //读超时
    protected long writeTimeOut;                          //写超时
    protected long connectTimeout;                        //链接超时
    protected int retryCount;                             //重试次数默认3次
    protected int retryDelay;                             //延迟xxms重试
    protected int retryIncreaseDelay;                     //叠加延迟
    protected boolean isSyncRequest;                      //是否是同步请求
    protected List<Cookie> cookies = new ArrayList<>();   //用户手动添加的Cookie
    protected HttpHeaders headers = new HttpHeaders();    //添加的header
    protected HttpParams params = new HttpParams();       //添加的param
    protected Retrofit retrofit;                          //支持Retrofit的扩展
    protected RxCache rxCache;                            //rxcache缓存
    protected ApiService apiManager;                      //通用的的api接口
    protected OkHttpClient okHttpClient;                  //支持OkHttpClient的拓展
    protected Context context;                            //上下文
    private boolean sign = false;                         //是否需要签名
    private boolean timeStamp = false;                    //是否需要追加时间戳
    private boolean accessToken = false;                  //是否需要追加token
    protected HttpUrl httpUrl;                            //自己组合成目标HttpUrl
    protected Proxy proxy;                                //代理
    protected HttpsUtils.SSLParams sslParams;             //https认证需要的核心参数封装对象
    protected HostnameVerifier hostnameVerifier;          //此类是用于主机名验证的基接口。
                                                          // 在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，
                                                          // 则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。
                                                          // 如果有双域名时可通过此对象提供一个主机白名单
    protected List<Converter.Factory> converterFactories = new ArrayList<>();//Retrofit的Covert
    protected List<CallAdapter.Factory> adapterFactories = new ArrayList<>();//Retrogit的CallAdapter

    protected boolean isEncrypt;//是否加密
    protected IParamConvert paramConvert;//参数转换器
    /**
     * 应用拦截器
     *  不必要担心响应和重定向之间的中间响应。
     *  通常只调用一次，即使HTTP响应是通过缓存提供的。
     *  遵从应用层的最初目的。与OkHttp的注入头部无关，如If-None-Match。
     *  允许短路而且不调用Chain.proceed()。
     *  允许重试和多次调用Chain.proceed()。
     * 网络拦截器
     *  允许像重定向和重试一样操作中间响应。
     *  网络发生短路时不调用缓存响应。
     *  在数据被传递到网络时观察数据。
     *  有权获得装载请求的连接。
     */
    protected final List<Interceptor> networkInterceptors = new ArrayList<>();//网络拦截器，拦截器按列表顺序调用
    protected final List<Interceptor> interceptors = new ArrayList<>();//应用拦截器，拦截器按列表顺序调用


    public BaseRequest(String url) {
        this.url = url;
        //采用模型拷贝方式初始化参数
        context = EasyHttp.getContext();
        EasyHttp config = EasyHttp.getInstance();
        this.baseUrl = config.getBaseUrl();
        if (!TextUtils.isEmpty(this.baseUrl))
            httpUrl = HttpUrl.parse(baseUrl);
        cacheMode = config.getCacheMode();                                //添加缓存模式
        cacheTime = config.getCacheTime();                                //缓存时间
        retryCount = config.getRetryCount();                              //超时重试次数
        retryDelay = config.getRetryDelay();                              //超时重试延时
        retryIncreaseDelay = config.getRetryIncreaseDelay();              //超时重试叠加延时
        //Okhttp  cache
        cache = config.getHttpCache();
        isEncrypt = config.isEncrypt();
        paramConvert = config.getParamConvert();
        //默认添加 Accept-Language
        String acceptLanguage = HttpHeaders.getAcceptLanguage();
        if (!TextUtils.isEmpty(acceptLanguage))
            headers(HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE, acceptLanguage);
        //默认添加 User-Agent
        String userAgent = HttpHeaders.getUserAgent();
        if (!TextUtils.isEmpty(userAgent)) headers(HttpHeaders.HEAD_KEY_USER_AGENT, userAgent);
        //如果有的话，添加公共请求参数
        if (config.getCommonParams() != null) params.put(config.getCommonParams());
        //如果有的话，添加公共请求头
        if (config.getCommonHeaders() != null) headers.put(config.getCommonHeaders());
    }

    public HttpParams getParams() {
        return this.params;
    }

    public R readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return (R) this;
    }

    public R writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return (R) this;
    }

    public R connectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return (R) this;
    }

    public R okCache(Cache cache) {
        this.cache = cache;
        return (R) this;
    }

    public R cacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
        return (R) this;
    }

    public R cacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
        return (R) this;
    }

    public R cacheTime(long cacheTime) {
        if (cacheTime <= -1) cacheTime = EasyHttp.DEFAULT_CACHE_NEVER_EXPIRE;
        this.cacheTime = cacheTime;
        return (R) this;
    }

    public R baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        if (!TextUtils.isEmpty(this.baseUrl))
            httpUrl = HttpUrl.parse(baseUrl);
        return (R) this;
    }

    public R retryCount(int retryCount) {
        if (retryCount < 0) throw new IllegalArgumentException("retryCount must > 0");
        this.retryCount = retryCount;
        return (R) this;
    }

    public R retryDelay(int retryDelay) {
        if (retryDelay < 0) throw new IllegalArgumentException("retryDelay must > 0");
        this.retryDelay = retryDelay;
        return (R) this;
    }

    public R retryIncreaseDelay(int retryIncreaseDelay) {
        if (retryIncreaseDelay < 0)
            throw new IllegalArgumentException("retryIncreaseDelay must > 0");
        this.retryIncreaseDelay = retryIncreaseDelay;
        return (R) this;
    }

    public R addInterceptor(Interceptor interceptor) {
        interceptors.add(HttpUtil.checkNotNull(interceptor, "interceptor == null"));
        return (R) this;
    }

    public R addNetworkInterceptor(Interceptor interceptor) {
        networkInterceptors.add(HttpUtil.checkNotNull(interceptor, "interceptor == null"));
        return (R) this;
    }

    public R addCookie(String name, String value) {
        Cookie.Builder builder = new Cookie.Builder();
        Cookie cookie = builder.name(name).value(value).domain(httpUrl.host()).build();
        this.cookies.add(cookie);
        return (R) this;
    }

    public R addCookie(Cookie cookie) {
        this.cookies.add(cookie);
        return (R) this;
    }

    public R addCookies(List<Cookie> cookies) {
        this.cookies.addAll(cookies);
        return (R) this;
    }

    /**
     * 设置Converter.Factory,默认GsonConverterFactory.create()
     */
    public R addConverterFactory(Converter.Factory factory) {
        converterFactories.add(factory);
        return (R) this;
    }

    /**
     * 设置CallAdapter.Factory,默认RxJavaCallAdapterFactory.create()
     */
    public R addCallAdapterFactory(CallAdapter.Factory factory) {
        adapterFactories.add(factory);
        return (R) this;
    }

    /**
     * 设置代理
     */
    public R okproxy(Proxy proxy) {
        this.proxy = proxy;
        return (R) this;
    }

    /**
     * 设置缓存的转换器
     */
    public R cacheDiskConverter(IDiskConverter converter) {
        this.diskConverter = HttpUtil.checkNotNull(converter, "converter == null");
        return (R) this;
    }

    /**
     * https的全局访问规则
     */
    public R hostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return (R) this;
    }

    /**
     * https的全局自签名证书
     */
    public R certificates(InputStream... certificates) {
        this.sslParams = HttpsUtils.getSslSocketFactory(null, null, certificates);
        return (R) this;
    }

    /**
     * https双向认证证书
     */
    public R certificates(InputStream bksFile, String password, InputStream... certificates) {
        this.sslParams = HttpsUtils.getSslSocketFactory(bksFile, password, certificates);
        return (R) this;
    }

    /**
     * 添加头信息
     */
    public R headers(HttpHeaders headers) {
        this.headers.put(headers);
        return (R) this;
    }

    /**
     * 添加头信息
     */
    public R headers(String key, String value) {
        headers.put(key, value);
        return (R) this;
    }

    /**
     * 移除头信息
     */
    public R removeHeader(String key) {
        headers.remove(key);
        return (R) this;
    }

    /**
     * 移除所有头信息
     */
    public R removeAllHeaders() {
        headers.clear();
        return (R) this;
    }

    /**
     * 设置参数
     */
    public R params(HttpParams params) {
        this.params.put(params);
        return (R) this;
    }

    public R params(String key, String value) {
        params.put(key, value);
        return (R) this;
    }

    public R removeParam(String key) {
        params.remove(key);
        return (R) this;
    }

    public R removeAllParams() {
        params.clear();
        return (R) this;
    }

    public R sign(boolean sign) {
        this.sign = sign;
        return (R) this;
    }

    public R timeStamp(boolean timeStamp) {
        this.timeStamp = timeStamp;
        return (R) this;
    }

    public R accessToken(boolean accessToken) {
        this.accessToken = accessToken;
        return (R) this;
    }

    public R syncRequest(boolean syncRequest) {
        this.isSyncRequest = syncRequest;
        return (R) this;
    }
    public R setEncrypt(boolean encrypt) {
        this.isEncrypt = encrypt;
        return (R) this;
    }
    public R setParamConvert(IParamConvert pc) {
        this.paramConvert = pc;
        return (R) this;
    }

    /**
     * 移除缓存（key）
     */
    public void removeCache(String key) {
        EasyHttp.getRxCache().remove(key).compose(RxTransform.<Boolean>io_main())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        PrintLog.i("removeCache success!!!");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        PrintLog.i("removeCache err!!!"+throwable);
                    }
                });
    }

    /**
     * 通用的根据当前的请求参数，生成对应的OkClient.Builder
     */
    private OkHttpClient.Builder generateOkClient() {
        if (readTimeOut <= 0 && writeTimeOut <= 0 && connectTimeout <= 0 && sslParams == null
                && cookies.size() == 0 && hostnameVerifier == null && proxy == null && headers.isEmpty()) {
            //如果没有单独设置采用EasyHttp的builder
            OkHttpClient.Builder builder = EasyHttp.getOkHttpClientBuilder();
            for (Interceptor interceptor : builder.interceptors()) {
                if (interceptor instanceof BaseDynamicInterceptor) {
                    //如果有动态的拦截器则对其设置签名，时间戳等额外信息
                    ((BaseDynamicInterceptor) interceptor).sign(sign).timeStamp(timeStamp).accessToken(accessToken);
                }
            }
            return builder;
        } else {
            //根据当前的参数重新配置Builder
            final OkHttpClient.Builder newClientBuilder = EasyHttp.getOkHttpClient().newBuilder();
            if (readTimeOut > 0)
                newClientBuilder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
            if (writeTimeOut > 0)
                newClientBuilder.writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS);
            if (connectTimeout > 0)
                newClientBuilder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
            if (hostnameVerifier != null) newClientBuilder.hostnameVerifier(hostnameVerifier);
            if (sslParams != null)
                newClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
            if (proxy != null) newClientBuilder.proxy(proxy);
            if (cookies.size() > 0) EasyHttp.getCookieJar().addCookies(cookies);

            //添加头  头添加放在最前面方便其他拦截器可能会用到
            newClientBuilder.addInterceptor(new HeadersInterceptor(headers));
            //本类设置的应用拦截器改造后设置
            for (Interceptor interceptor : interceptors) {
                if (interceptor instanceof BaseDynamicInterceptor) {
                    ((BaseDynamicInterceptor) interceptor).sign(sign).timeStamp(timeStamp).accessToken(accessToken);
                }
                newClientBuilder.addInterceptor(interceptor);
            }
            //已经设置的应用拦截器改造
            for (Interceptor interceptor : newClientBuilder.interceptors()) {
                if (interceptor instanceof BaseDynamicInterceptor) {
                    ((BaseDynamicInterceptor) interceptor).sign(sign).timeStamp(timeStamp).accessToken(accessToken);
                }
            }
            //添加网络拦截器
            if (networkInterceptors.size() > 0) {
                for (Interceptor interceptor : networkInterceptors) {
                    newClientBuilder.addNetworkInterceptor(interceptor);
                }
            }
            return newClientBuilder;
        }
    }

    /**
     * 通用根据当前的请求参数，生成对应的Retrofit
     */
    private Retrofit.Builder generateRetrofit() {
        if (converterFactories.isEmpty() && adapterFactories.isEmpty()) {
            //没有converter和adapter时返回一个EasyHttp的只有baseUrl
            return EasyHttp.getRetrofitBuilder().baseUrl(baseUrl);
        } else {
            //如果重新指定了则重新添加
            final Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            if (!converterFactories.isEmpty()) {
                for (Converter.Factory converterFactory : converterFactories) {
                    retrofitBuilder.addConverterFactory(converterFactory);
                }
            } else {
                //获取全局的对象重新设置
                List<Converter.Factory> listConverterFactory = EasyHttp.getRetrofit().converterFactories();
                for (Converter.Factory factory : listConverterFactory) {
                    retrofitBuilder.addConverterFactory(factory);
                }
            }
            if (!adapterFactories.isEmpty()) {
                for (CallAdapter.Factory adapterFactory : adapterFactories) {
                    retrofitBuilder.addCallAdapterFactory(adapterFactory);
                }
            } else {
                //获取全局的对象重新设置
                List<CallAdapter.Factory> listAdapterFactory = EasyHttp.getRetrofit().callAdapterFactories();
                for (CallAdapter.Factory factory : listAdapterFactory) {
                    retrofitBuilder.addCallAdapterFactory(factory);
                }
            }
            return retrofitBuilder.baseUrl(baseUrl);
        }
    }

    /**
     * 通用的根据当前的请求参数，生成对应的RxCache.Builder
     */
    private RxCache.Builder generateRxCache() {
        final RxCache.Builder rxCacheBuilder = EasyHttp.getRxCacheBuilder();
        switch (cacheMode) {
            case NO_CACHE://不使用缓存
                final NoCacheInterceptor NOCACHEINTERCEPTOR = new NoCacheInterceptor();
                interceptors.add(NOCACHEINTERCEPTOR);
                networkInterceptors.add(NOCACHEINTERCEPTOR);
                break;
            case DEFAULT://使用Okhttp的缓存
                if (this.cache == null) {
                    File cacheDirectory = EasyHttp.getCacheDirectory();
                    if (cacheDirectory == null) {
                        cacheDirectory = new File(EasyHttp.getContext().getCacheDir(), "okhttp-cache");
                    } else {
                        if (cacheDirectory.isDirectory() && !cacheDirectory.exists()) {
                            cacheDirectory.mkdirs();
                        }
                    }
                    this.cache = new Cache(cacheDirectory, Math.max(5 * 1024 * 1024, EasyHttp.getCacheMaxSize()));
                }
                String cacheControlValue = String.format("max-age=%d", Math.max(-1, cacheTime));
                final CacheInterceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new CacheInterceptor(EasyHttp.getContext(), cacheControlValue);
                final CacheInterceptorOffline REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE = new CacheInterceptorOffline(EasyHttp.getContext(), cacheControlValue);
                networkInterceptors.add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
                networkInterceptors.add(REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE);
                interceptors.add(REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE);
                break;
            case FIRSTREMOTE:
            case FIRSTCACHE:
            case ONLYREMOTE:
            case ONLYCACHE:
            case CACHEANDREMOTE:
            case CACHEANDREMOTEDISTINCT://不适用Okhttp的缓存
                interceptors.add(new NoCacheInterceptor());
                if (diskConverter == null) {
                    final RxCache.Builder tempRxCacheBuilder = rxCacheBuilder;
                    tempRxCacheBuilder.cachekey(HttpUtil.checkNotNull(cacheKey, "cacheKey == null"))
                            .cacheTime(cacheTime);
                    return tempRxCacheBuilder;
                } else {
                    final RxCache.Builder cacheBuilder = EasyHttp.getRxCache().newBuilder();
                    cacheBuilder.diskConverter(diskConverter)
                            .cachekey(HttpUtil.checkNotNull(cacheKey, "cacheKey == null"))
                            .cacheTime(cacheTime);
                    return cacheBuilder;
                }
        }
        return rxCacheBuilder;
    }

    protected R build() {
        final RxCache.Builder rxCacheBuilder = generateRxCache();
        OkHttpClient.Builder okHttpClientBuilder = generateOkClient();
        if (cacheMode == CacheMode.DEFAULT) {//完全按照HTTP协议的默认缓存规则，走OKhttp的Cache缓存
            okHttpClientBuilder.cache(cache);
        }
        final Retrofit.Builder retrofitBuilder = generateRetrofit();
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());//增加RxJava2CallAdapterFactory
        okHttpClient = okHttpClientBuilder.build();
        retrofitBuilder.client(okHttpClient);
        retrofit = retrofitBuilder.build();
        rxCache = rxCacheBuilder.build();
        apiManager = retrofit.create(ApiService.class);
        return (R) this;
    }
    /**
     * 钩子方法为Rx加统一处理
     * 线程切换
     * 本地缓存策略添加
     * 重试机智添加
     */
    protected  <T> Observable<CacheResult<T>> toObservable(Observable observable, CallBackProxy<? extends ApiResult<T>, T> proxy) {
        return observable.map(new ApiResultFunc(proxy != null ? proxy.getType() : new TypeToken<ResponseBody>() {
        }.getType(),context))
                .compose(isSyncRequest ? HttpUtil._main() : HttpUtil._io_main())
                .compose(rxCache.transformer(cacheMode, proxy.getCallBack().getType()))
                .retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay));
    }
    /**
     * 抽象请求，不同的方式不同的实现
     * 这个是个钩子方法，不能从外部直接调。
     * @return
     */
    protected abstract Observable<ResponseBody> generateRequest();

}
