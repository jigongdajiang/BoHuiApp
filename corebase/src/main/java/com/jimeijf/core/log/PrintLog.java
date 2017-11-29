package com.jimeijf.core.log;

import android.text.TextUtils;
import android.util.Log;

/**
 * @author : gaojigong
 * @date : 2017/11/13
 * @description:
 */


public class PrintLog {
    public static String COMMONLOGTAG = "jmlog";

    private PrintLog() {
    }

    //打印哪些信息，可全局开关
    public static boolean allowD = true;
    public static boolean allowE = true;
    public static boolean allowI = true;
    public static boolean allowV = true;
    public static boolean allowW = true;
    public static boolean allowWtf = true;
    public static boolean allowJson = true;
    public static boolean allowXml = true;

    /**
     * 以线程信息作为Tag
     * @param caller
     * @return
     */
    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(COMMONLOGTAG) ? tag : COMMONLOGTAG + ":" + tag;
        return tag;
    }
    private static ILogStrategy logStrategy;

    public static void init(ILogStrategy ls){
        logStrategy = ls;
    }

    public interface ILogStrategy {
        void d(String tag, String content);

        void d(String tag, String content, Throwable tr);

        void e(String tag, String content);

        void e(String tag, String content, Throwable tr);

        void i(String tag, String content);

        void i(String tag, String content, Throwable tr);

        void v(String tag, String content);

        void v(String tag, String content, Throwable tr);

        void w(String tag, String content);

        void w(String tag, String content, Throwable tr);

        void w(String tag, Throwable tr);

        void wtf(String tag, String content);

        void wtf(String tag, String content, Throwable tr);

        void wtf(String tag, Throwable tr);

        void json(String json);

        void xml(String xml);
    }
    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
    public static void d(String content) {
        d(null,content);
    }
    public static void d(String tag,String content) {
        if (!allowD) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }
        if (logStrategy != null) {
            logStrategy.d(tag, content);
        } else {
            Log.d(tag, content);
        }
    }


    public static void d(String content, Throwable tr) {
        d(null,content,tr);
    }
    public static void d(String tag,String content, Throwable tr) {
        if (!allowD) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }

        if (logStrategy != null) {
            logStrategy.d(tag, content, tr);
        } else {
            Log.d(tag, content, tr);
        }
    }
    public static void e(String content) {
        e(null,content);
    }
    public static void e(String tag,String content) {
        if (!allowE) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }
        if (logStrategy != null) {
            logStrategy.e(tag, content);
        } else {
            Log.e(tag, content);
        }
    }
    public static void e(Exception e) {
        e(null,e);
    }
    public static void e(String tag,Exception e) {
        if (!allowE) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }

        if (logStrategy != null) {
            logStrategy.e(tag, e.getMessage(), e);
        } else {
            Log.e(tag, e.getMessage(), e);
        }
    }
    public static void e(String content, Throwable tr) {
        e(null,content,tr);
    }
    public static void e(String tag,String content, Throwable tr) {
        if (!allowE) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }

        if (logStrategy != null) {
            logStrategy.e(tag, content, tr);
        } else {
            Log.e(tag, content, tr);
        }
    }
    public static void i(String content) {
        i(null,content);
    }
    public static void i(String tag,String content) {
        if (!allowI) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }

        if (logStrategy != null) {
            logStrategy.i(tag, content);
        } else {
            Log.i(tag, content);
        }
    }

    public static void i(String content, Throwable tr) {
        i(null,content,tr);
    }
    public static void i(String tag,String content, Throwable tr) {
        if (!allowI) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }

        if (logStrategy != null) {
            logStrategy.i(tag, content, tr);
        } else {
            Log.i(tag, content, tr);
        }
    }

    public static void v(String content) {
        i(null,content);
    }
    public static void v(String tag,String content) {
        if (!allowV) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }

        if (logStrategy != null) {
            logStrategy.v(tag, content);
        } else {
            Log.v(tag, content);
        }
    }
    public static void v(String content, Throwable tr) {
        v(null,content,tr);
    }
    public static void v(String tag,String content, Throwable tr) {
        if (!allowV) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }

        if (logStrategy != null) {
            logStrategy.v(tag, content, tr);
        } else {
            Log.v(tag, content, tr);
        }
    }

    public static void w(String content) {
        w(null,content);
    }
    public static void w(String tag,String content) {
        if (!allowW) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }

        if (logStrategy != null) {
            logStrategy.w(tag, content);
        } else {
            Log.w(tag, content);
        }
    }
    public static void w(String content, Throwable tr) {
        w(null,content,tr);
    }
    public static void w(String tag,String content, Throwable tr) {
        if (!allowW) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }

        if (logStrategy != null) {
            logStrategy.w(tag, content, tr);
        } else {
            Log.w(tag, content, tr);
        }
    }
    public static void w(Throwable tr) {
        if (!allowW) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (logStrategy != null) {
            logStrategy.w(tag, tr);
        } else {
            Log.w(tag, tr);
        }
    }
    public static void wtf(String content) {
        w(null,content);
    }
    public static void wtf(String tag,String content) {
        if (!allowWtf) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }

        if (logStrategy != null) {
            logStrategy.wtf(tag, content);
        } else {
            Log.wtf(tag, content);
        }
    }
    public static void wtf(String content, Throwable tr) {
        Log.wtf(null, content,tr);
    }
    public static void wtf(String tag,String content, Throwable tr) {
        if (!allowWtf) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }

        if (logStrategy != null) {
            logStrategy.wtf(tag, content, tr);
        } else {
            Log.wtf(tag, content, tr);
        }
    }
    public static void wtf(Throwable tr) {
        if (!allowWtf) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (logStrategy != null) {
            logStrategy.wtf(tag, tr);
        } else {
            Log.wtf(tag, tr);
        }
    }

    public static void json(String json){
        json(null,json);
    }
    public static void json(String tag,String json){
        if (!allowJson) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }

        if (logStrategy != null) {
            logStrategy.json(json);
        } else {
            Log.e(tag, "no format,need use logger"+json);
        }
    }
    public static void xml(String xml){
        xml(null,xml);
    }
    public static void xml(String tag,String xml){
        if (!allowXml) return;
        if(TextUtils.isEmpty(tag)) {
            StackTraceElement caller = getCallerStackTraceElement();
            tag = generateTag(caller);
        }

        if (logStrategy != null) {
            logStrategy.xml(xml);
        } else {
            Log.e(tag, "no format,need use logger"+xml);
        }
    }
}
