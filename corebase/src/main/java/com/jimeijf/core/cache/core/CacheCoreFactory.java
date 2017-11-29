package com.jimeijf.core.cache.core;

import android.content.Context;

import com.jimeijf.core.cache.converter.IDiskConverter;
import com.jimeijf.core.cache.core.memory.MemoryCache;
import com.jimeijf.core.cache.core.preference.AbsObjectStrategy;
import com.jimeijf.core.cache.core.preference.PreferenceCache;

import java.io.File;

/**
 * @author : gongdaocai
 * @date : 2017/11/14
 * FileName:
 * @description:
 */


public class CacheCoreFactory {
    public static CacheCore getPreferenceCache(Context context, String pName, AbsObjectStrategy strategy){
        return new CacheCore(new PreferenceCache(context,pName,strategy));
    }
    public static CacheCore getPreferenceCache(Context context, String pName){
        return getPreferenceCache(context,pName,null);
    }
    public static CacheCore getPreferenceCache(Context context){
        return getPreferenceCache(context,null);
    }

    public static CacheCore getLruDiskCache(Context context,IDiskConverter diskConverter, File diskDir, int appVersion, long diskMaxSize){
        return new CacheCore(new LruDiskCache(context,diskConverter,diskDir,appVersion,diskMaxSize));
    }
    public static CacheCore getLruDiskCache(Context context ,File diskDir){
        return new CacheCore(new LruDiskCache(context,null,diskDir,0,0));
    }
    public static CacheCore getMemoryCache(){
        return new CacheCore(new MemoryCache());
    }
}
