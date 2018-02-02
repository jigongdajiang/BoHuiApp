package com.framework.core.util;

import android.content.Context;
import android.os.Environment;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author : gaojigong
 * @date : 2017/11/16
 * @description:
 */


public class FileUtil {
    /**
     * 关闭流等资源使用的公共方法
     */
    public static void close(Closeable close) {
        if (close != null) {
            try {
                closeThrowException(close);
            } catch (IOException ignored) {
            }
        }
    }

    public static void closeThrowException(Closeable close) throws IOException {
        if (close != null) {
            close.close();
        }
    }

    /**
     * 获取程序缓存目录，优先获取外部存储器
     */
    public static String getCacheDir(Context context) {
        return (null != getExternalCacheDir(context)) ? getExternalCacheDir(context)
                : getInternalCacheDir(context);
    }

    /**
     * 获取内部缓存目录
     *
     * @param context Context
     * @return 内部存储器不可用时，返回null，否则返回String对象;
     */
    private static String getInternalCacheDir(Context context) {
        return context.getCacheDir().getAbsolutePath() + "/";
    }

    /**
     * 获取外部缓存目录
     *
     * @param context Context
     * @return 外部存储器不可用时，返回null，否则返回String对象;
     */
    private static String getExternalCacheDir(Context context) {
        if (isExternalStorageAvailable()) {
            String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
            String path = sdcard + "bohui/";
            File cache = new File(path);
            cache.mkdirs();
            return path;
        }
        return null;
    }

    /**
     * 外部存储器是否可用
     *
     * @return 可用返回true，否则返回false;
     */
    private static boolean isExternalStorageAvailable() {
        return isHaveExternalStorage() && !isExternalStorageReadOnly();
    }

    /**
     * 外部存储器是否存在
     *
     * @return 存在返回true，否则返回false;
     */
    private static boolean isHaveExternalStorage() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    /**
     * 外部存储器是否为只读
     *
     * @return 只读返回true，否则返回false;
     */
    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    public static String generateFileName() {
        return UUID.randomUUID().toString();
    }
}
