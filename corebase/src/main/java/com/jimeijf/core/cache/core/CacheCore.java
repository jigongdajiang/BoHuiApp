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

package com.jimeijf.core.cache.core;

import com.jimeijf.core.log.PrintLog;
import com.jimeijf.core.util.CUtil;

import java.lang.reflect.Type;

import okio.ByteString;

/**
 * <p>描述：缓存核心管理类</p>
 * <p>
 * 1.可以指定不同的缓存方式<br>
 * 1.对Key进行MD5加密<br>
 * <p>
 * 以后可以扩展 增加内存缓存，但是内存缓存的时间不好控制，暂未实现，后续可以添加》<br>
 * <p>
 * 作者： zhouyou<br>
 * 日期： 2016/12/24 10:35<br>
 * 版本： v2.0<br>
 * <p>
 * 修改者： zhouyou
 * 日期： 2017/01/06 10:35<br>
 * 1.这里笔者给读者留个提醒，ByteString其实已经很强大了，不需要我们自己再去处理加密了，只要善于发现br>
 * 2.这里为设么把MD5改成ByteString呢？其实改不改无所谓，只是想把ByteString这个好东西介绍给大家。(ok)br>
 * 3.在ByteString中有很多好用的方法包括MD5.sha1 base64  encodeUtf8 等等功能。br>
 */
public class CacheCore {

    private BaseCache cache;

    public CacheCore(BaseCache cache) {
        this.cache = CUtil.checkNotNull(cache, "cache==null");
    }


    /**
     * 读取
     */
    public synchronized <T> T load(Type type, String key, long time) {
        //对key进行md5加密
        String cacheKey = ByteString.of(key.getBytes()).md5().hex();
        PrintLog.d("loadCache  key=" + cacheKey);
        if (cache != null) {
            T result = cache.load(type, cacheKey, time);
            if (result != null) {
                return result;
            }
        }

        return null;
    }
    public synchronized <T> T load(Type type, String key) {
        return load(type,key,-1);
    }

    /**
     * 保存
     */
    public synchronized <T> boolean save(String key, T value) {
        String cacheKey = ByteString.of(key.getBytes()).md5().hex();
        PrintLog.d("saveCache  key=" + cacheKey);
        return cache.save(cacheKey, value);
    }

    /**
     * 是否包含
     *
     * @param key
     * @return
     */
    public synchronized boolean containsKey(String key) {
        String cacheKey = ByteString.of(key.getBytes()).md5().hex();
        PrintLog.d("containsCache  key=" + cacheKey);
        if (cache != null) {
            if (cache.containsKey(cacheKey)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public synchronized boolean remove(String key) {
        String cacheKey = ByteString.of(key.getBytes()).md5().hex();
        PrintLog.d("removeCache  key=" + cacheKey);
        if (cache != null) {
            return cache.remove(cacheKey);
        }
        return true;
    }

    /**
     * 清空缓存
     */
    public synchronized boolean clear() {
        if (cache != null) {
            return cache.clear();
        }
        return false;
    }

}
