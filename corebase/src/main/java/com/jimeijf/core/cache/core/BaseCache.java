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


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jimeijf.core.log.PrintLog;
import com.jimeijf.core.util.CUtil;
import com.jimeijf.core.util.TUtil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>描述：缓存的基类</p>
 * 1.所有缓存处理都继承该基类<br>
 * 2.增加了锁机制，防止频繁读取缓存造成的异常。<br>
 * 3.子类直接考虑具体的实现细节就可以了。<br>
 * 4.实质就是对读写操作进行优化包装，保证并发的安全且能有尽可能高的效率<br>
 * 5.可基于此类实现多种类型的缓存，如Lru缓存，Memory缓存，Preference缓存，数据库缓存等<br>
 * <p>
 * 作者： zhouyou<br>
 * 日期： 2016/01/07 12:35<br>
 * 版本： v2.0<br>
 */
public abstract class BaseCache {
    public static final int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024; // 5MB 最小默认缓存大小
    public static final int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB 最大默认缓存大小
    public static final long CACHE_NEVER_EXPIRE = -1;//永久不过期
    /**
     * synchronized Java SE 1.6对它进行了优化
     * 有4种状态：无锁、偏向锁、轻量级锁、重量级锁，他们性能消耗是由低向高的.
     * 当线程竞争时，synchronized会升级成重量级锁，当一个线程持有锁时，其他的线程只能阻塞等待，有些时候会给我们带来不必要的性能损耗。
     *
     * ReentrantReadWriteLock是这样一种机制，就是当有写者正在写的时候，他是独占资源的，其他无论读者还是写者只能阻塞等待；
     * 当没有写者正在写的时候，读者们是可以并行读到数据的，这样当写着很少，读者很多的时候，读者们几乎可以同时完成读的操作，
     * 这样就大大提升了程序的运行效率。
     */
    private final ReadWriteLock mLock = new ReentrantReadWriteLock();



    /**
     * 读取缓存
     *
     * @param key       缓存key
     * @param existTime 缓存时间
     */
    final <T> T load(Type type, String key, long existTime) {
        //1.先检查key
        CUtil.checkNotNull(key, "key == null");

        //2.判断key是否存在,key不存在去读缓存没意义
        if (!containsKey(key)) {
            return null;
        }

        //3.判断是否过期，过期自动清理
        if (isExpiry(key, existTime)) {
            remove(key);
            return null;
        }

        //4.开始真正的读取缓存
        mLock.readLock().lock();
        try {
            // 读取缓存
            return doLoad(type, key);
        } finally {
            mLock.readLock().unlock();
        }
    }

    /**
     * 保存缓存
     *
     * @param key   缓存key
     * @param value 缓存内容
     * @return
     */
    final <T> boolean save(String key, T value) {
        //1.先检查key
        CUtil.checkNotNull(key, "key == null");

        //2.如果要保存的值为空,则删除
        if (value == null) {
            return remove(key);
        }

        //3.写入缓存
        boolean status = false;
        mLock.writeLock().lock();
        try {
            status = doSave(key, value);
        } finally {
            mLock.writeLock().unlock();
        }
        return status;
    }

    /**
     * 删除缓存
     */
    final boolean remove(String key) {
        mLock.writeLock().lock();
        try {
            return doRemove(key);
        } finally {
            mLock.writeLock().unlock();
        }
    }

    /**
     * 清空缓存
     */
    final boolean clear() {
        mLock.writeLock().lock();
        try {
            return doClear();
        } finally {
            mLock.writeLock().unlock();
        }
    }

    /**
     * 是否包含 加final 是让子类不能被重写，只能使用doContainsKey<br>
     * 这里加了锁处理，操作安全。<br>
     *
     * @param key 缓存key
     * @return 是否有缓存
     */
    public final boolean containsKey(String key) {
        mLock.readLock().lock();
        try {
            return doContainsKey(key);
        } finally {
            mLock.readLock().unlock();
        }
    }

    /**
     * 是否包含  采用protected修饰符  被子类修改
     */
    protected abstract boolean doContainsKey(String key);

    /**
     * 是否过期
     */
    protected abstract boolean isExpiry(String key, long existTime);

    /**
     * 读取缓存
     */
    protected abstract <T> T doLoad(Type type, String key);

    /**
     * 保存
     */
    protected abstract <T> boolean doSave(String key, T value);

    /**
     * 删除缓存
     */
    protected abstract boolean doRemove(String key);

    /**
     * 清空缓存
     */
    protected abstract boolean doClear();


    /**
     * 计算缓存大小
     * 规则
     *  A :总存储空间的 1/50
     *  MAX :最大缓存空间 50M
     *  MIN :最小的缓存空间5M
     *  总容量的 1/50
     *      如果大于MAX则为MAX
     *      如果小于MIN则为MIN
     *      如果在 MIN和MAX之间则取总容量的1/50
     *
     * @param dir
     * @return
     */
    public static long calculateDiskCacheSize(File dir) {
        long size = 0;
        try {
            //StatFs 用于获取存储空间
            StatFs statFs = new StatFs(dir.getAbsolutePath());
            //getBlockCount() 文件系统中总的存储区块的数量
            //getBlockSize() 文件系统中每个存储区块的字节数
            long available = ((long) statFs.getBlockCount()) * statFs.getBlockSize();
            size = available / 50;
        } catch (IllegalArgumentException ignored) {
        }
        return Math.max(Math.min(size, BaseCache.MAX_DISK_CACHE_SIZE),BaseCache.MIN_DISK_CACHE_SIZE);
    }

    /**
     * 应用程序缓存原理：
     * 1.当SD卡存在或者SD卡不可被移除的时候，就调用getExternalCacheDir()方法来获取缓存路径，否则就调用getCacheDir()方法来获取缓存路径<br>
     * 2.前者是/sdcard/Android/data/<application package>/cache 这个路径<br>
     * 3.后者获取到的是 /data/data/<application package>/cache 这个路径<br>
     *
     * @param uniqueName 缓存目录
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        File cacheDir;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cacheDir = context.getExternalCacheDir();
        } else {
            cacheDir = context.getCacheDir();
        }
        if (cacheDir == null) {// if cacheDir is null throws NullPointerException
            cacheDir = context.getCacheDir();
        }
        return new File(cacheDir.getPath() + File.separator + uniqueName);
    }

}
