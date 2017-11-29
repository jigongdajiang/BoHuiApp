package com.jimeijf.core.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author : gaojigong
 * @date : 2017/11/13
 * @description:
 */


public class CUtil {
    public static <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }
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
}
