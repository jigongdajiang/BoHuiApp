package com.jimeijf.core.cache.core.memory;

import java.lang.ref.SoftReference;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : gongdaocai
 * @date : 2017/11/14
 * FileName:
 * @description:
 */


public class MCache {
    private static MCache mInstance;
    /**
     * 内存缓存
     */
    private ConcurrentHashMap<String, SoftReference<Object>> maps;
    private MCache(){
        maps = new ConcurrentHashMap<>();
    }
    public static MCache getInstance(){
        if(mInstance == null){
            synchronized (MCache.class){
                if(mInstance == null){
                    mInstance = new MCache();
                }
            }
        }
        return mInstance;
    }
    protected boolean doContainsKey(String key) {
        return maps.containsKey(key);
    }

    protected <T> T doLoad(String key) {
        maps.get(key);
        return (T)maps.get(key).get();
    }

    protected <T> boolean doSave(String key, T value) {
        return maps.put(key,new SoftReference<Object>(value)) != null ? true : false;
    }

    protected boolean doRemove(String key) {
        return maps.remove(key) != null ? true : false;
    }

    protected boolean doClear() {
        maps.clear();
        return true;
    }
}
