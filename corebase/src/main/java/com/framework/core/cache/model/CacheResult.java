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

package com.framework.core.cache.model;


import java.io.Serializable;

/**
 * <p>描述：缓存对象</p>
 * 通过缓存策略会将目标对像包装成该类返回
 * 主要是用来区分数据是来自缓存还是来自网络
 * 作者： zhouyou<br>
 * 日期： 2016/12/24 10:35<br>
 * 版本： v2.0<br>
 */
public class CacheResult<T> implements Serializable {
    public boolean isFromCache;
    public T data;

    public CacheResult() {
    }

    public CacheResult(boolean isFromCache) {
        this.isFromCache = isFromCache;
    }

    public CacheResult(boolean isFromCache, T data) {
        this.isFromCache = isFromCache;
        this.data = data;
    }

    public boolean isCache() {
        return isFromCache;
    }

    public void setCache(boolean cache) {
        isFromCache = cache;
    }

    @Override
    public String toString() {
        return "CacheResult{" +
                "isCache=" + isFromCache +
                ", data=" + data +
                '}';
    }
}
