package com.jimeijf.core.log;

import android.text.TextUtils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @author : gaojigong
 * @date : 2017/11/13
 * @FileName:
 * @description:
 */


public class LoggerStrategy implements PrintLog.ILogStrategy {
    public LoggerStrategy(){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)         // （可选）要显示的方法行数。 默认2
                .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。 默认5
                .logStrategy(new LogcatLogStrategy()) //（可选）更改要打印的日志策略。 默认LogCat
                .tag(PrintLog.COMMONLOGTAG)   //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
    @Override
    public void d(String tag, String content) {
        Logger.log(Logger.DEBUG,tag,content,null);
    }

    @Override
    public void d(String tag, String content, Throwable tr) {
        Logger.log(Logger.DEBUG,tag,content,tr);
    }

    @Override
    public void e(String tag, String content) {
        Logger.log(Logger.ERROR,tag,content,null);
    }

    @Override
    public void e(String tag, String content, Throwable tr) {
        Logger.log(Logger.ERROR,tag,content,tr);
    }

    @Override
    public void i(String tag, String content) {
        Logger.log(Logger.INFO,tag,content,null);
    }

    @Override
    public void i(String tag, String content, Throwable tr) {
        Logger.log(Logger.INFO,tag,content,tr);
    }

    @Override
    public void v(String tag, String content) {
        Logger.log(Logger.VERBOSE,tag,content,null);
    }

    @Override
    public void v(String tag, String content, Throwable tr) {
        Logger.log(Logger.VERBOSE,tag,content,tr);
    }

    @Override
    public void w(String tag, String content) {
        Logger.log(Logger.WARN,tag,content,null);
    }

    @Override
    public void w(String tag, String content, Throwable tr) {
        Logger.log(Logger.WARN,tag,content,tr);
    }

    @Override
    public void w(String tag, Throwable tr) {
        Logger.log(Logger.WARN,tag,"null",tr);
    }

    @Override
    public void wtf(String tag, String content) {
        Logger.log(Logger.ASSERT,tag,content,null);
    }

    @Override
    public void wtf(String tag, String content, Throwable tr) {
        Logger.log(Logger.ASSERT,tag,content,tr);
    }

    @Override
    public void wtf(String tag, Throwable tr) {
        Logger.log(Logger.ASSERT,tag,"null",tr);
    }


    @Override
    public void json(String json) {
        Logger.json(json);
    }

    @Override
    public void xml(String xml) {
        Logger.xml(xml);
    }
}
