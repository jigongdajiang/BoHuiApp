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

package com.jimeijf.core.cache.stategy;


import com.jimeijf.core.cache.RxCache;
import com.jimeijf.core.cache.model.CacheResult;
import com.jimeijf.core.log.PrintLog;

import java.lang.reflect.Type;
import java.util.ConcurrentModificationException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * <p>描述：实现缓存策略的基类</p>
 * 1.提供了结合网络请求时的一些通用的加载缓存的方法
 * 作者： zhouyou<br>
 * 日期： 2016/12/24 10:35<br>
 * 版本： v2.0<br>
 */
public abstract class BaseStrategy implements IStrategy {
    /**
     * 加载缓存
     */
    <T> Observable<CacheResult<T>> loadCache(final RxCache rxCache, Type type, final String key, final long time, final boolean needEmpty) {
        Observable<CacheResult<T>> observable = rxCache.<T>load(type, key, time)//这里是加载缓存的核心逻辑
                .flatMap(new Function<T, ObservableSource<CacheResult<T>>>() {
            @Override
            public ObservableSource<CacheResult<T>> apply(@NonNull T t) throws Exception {
                if (t == null) {
                    return Observable.error(new NullPointerException("Not find the cache!"));
                }
                //将其包装成CacheResult类型，以方便回调时区分
                return Observable.just(new CacheResult<T>(true, t));
            }
        });
        //如果没有缓存，也需要走到onComplete
        if (needEmpty) {
            ////onErrorResumeNext
            /// 源Observable正常的时候由这个操作符新建的Observable把源Observable释放的item直接释放释放出去。
            //一旦源Observable遇到错误，这个onErrorResumeNext会把源Observable用一个新的Observable替掉，
            // 然后这个新的Observable如果没遇到什么问题就会释放item给Observer。你可以直接将一个Observable实例
            // 传入onErrorResumeNext作为这个新的Observable，也可以传给onErrorResumeNext一个Func1实现，
            // 这个Func1实现接受源Observable的错误作为参数，返回新的Observable。
            observable = observable
                    .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends CacheResult<T>>>() {
                        @Override
                        public ObservableSource<? extends CacheResult<T>> apply(@NonNull Throwable throwable) throws Exception {
                            return Observable.empty();//返回Obervable 不执行任何其他操作，直接执行订阅者的onComplete()方法
                        }
                    });
        }
        return observable;
    }

    /**
     * 异步的方式 将请求的返回先保存，再将保存的返回
     */
    <T> Observable<CacheResult<T>> loadRemoteFirstSave(final RxCache rxCache, final String key, Observable<T> source, final boolean needEmpty) {
        Observable<CacheResult<T>> observable = source
                .map(new Function<T, CacheResult<T>>() {
                    @Override
                    public CacheResult<T> apply(@NonNull T t) throws Exception {
                        PrintLog.i("loadRemote result=" + t);
                        rxCache.save(key, t)
                                .subscribeOn(Schedulers.io())//保存工作运行在子线程
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(@NonNull Boolean status) throws Exception {
                                        PrintLog.i("save status => " + status);
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(@NonNull Throwable throwable) throws Exception {
                                        if (throwable instanceof ConcurrentModificationException) {
                                            PrintLog.i("Save failed, please use a synchronized cache strategy :", throwable);
                                        } else {
                                            PrintLog.i(throwable.getMessage());
                                        }
                                    }
                                });
                        return new CacheResult<T>(false, t);
                    }
                });
        if (needEmpty) {
            observable = observable
                    .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends CacheResult<T>>>() {
                        @Override
                        public ObservableSource<? extends CacheResult<T>> apply(@NonNull Throwable throwable) throws Exception {
                            return Observable.empty();
                        }
                    });
        }
        return observable;
    }

    /**
     * 同步的方式 将请求的返回先保存，再将保存的返回
     */
    <T> Observable<CacheResult<T>> loadRemote(final RxCache rxCache, final String key, Observable<T> source, final boolean needEmpty) {
        Observable<CacheResult<T>> observable = source
                .flatMap(new Function<T, ObservableSource<CacheResult<T>>>() {
                    @Override
                    public ObservableSource<CacheResult<T>> apply(final @NonNull T t) throws Exception {
                        return  rxCache.save(key, t).map(new Function<Boolean, CacheResult<T>>() {
                            @Override
                            public CacheResult<T> apply(@NonNull Boolean aBoolean) throws Exception {
                                PrintLog.i("save status => " + aBoolean);
                                return new CacheResult<T>(false, t);
                            }
                        }).onErrorReturn(new Function<Throwable, CacheResult<T>>() {
                            @Override
                            public CacheResult<T> apply(@NonNull Throwable throwable) throws Exception {
                                PrintLog.i("save status => " + throwable);
                                return new CacheResult<T>(false, t);
                            }
                        });
                    }
                });
        if (needEmpty) {
            observable = observable
                    .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends CacheResult<T>>>() {
                        @Override
                        public ObservableSource<? extends CacheResult<T>> apply(@NonNull Throwable throwable) throws Exception {
                            return Observable.empty();
                        }
                    });
        }
        return observable;
    }
}
