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

package com.jimeijf.core.http.body;


import com.jimeijf.core.http.callback.ProgressResponseCallBack;
import com.jimeijf.core.log.PrintLog;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * <p>描述：上传请求体</p>
 * 1.具有上传进度回调通知功能<br>
 * 2.防止频繁回调，上层无用的刷新<br>
 * 3.是对上传文件时的原RequestBody的装饰拓展与改造<br>
 * 作者： zhouyou<br>
 * 日期： 2016/12/23 16:38 <br>
 * 版本： v1.0<br>
 */
public class UploadProgressRequestBody extends RequestBody {

    protected RequestBody delegate;
    protected ProgressResponseCallBack progressCallBack;

    protected CountingSink countingSink;

    public UploadProgressRequestBody(ProgressResponseCallBack listener) {
        this.progressCallBack = listener;
    }

    public UploadProgressRequestBody(RequestBody delegate, ProgressResponseCallBack progressCallBack) {
        this.delegate = delegate;
        this.progressCallBack = progressCallBack;
    }

    public void setRequestBody(RequestBody delegate) {
        this.delegate = delegate;
    }

    @Override
    public MediaType contentType() {
        return delegate.contentType();

    }

    /**
     * 重写调用实际的响应体的contentLength
     */
    @Override
    public long contentLength() {
        try {
            return delegate.contentLength();
        } catch (IOException e) {
            PrintLog.e(e.getMessage());
            return -1;
        }
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        BufferedSink bufferedSink;
        //进行计时行Sink包装
        countingSink = new CountingSink(sink);
        //返回包装后的BufferedSink
        bufferedSink = Okio.buffer(countingSink);
        delegate.writeTo(bufferedSink);
        bufferedSink.flush();
    }


    /**
     * ForwardingSink是okio中的一个具有委托功能的抽象类，实现自Sink，采用的装饰器模式
     * 这个类可在写文件时(上传文件时)作为sink出入，能确保回调具有间隔，防止回调过于频繁
     */
    protected final class CountingSink extends ForwardingSink {
        private long bytesWritten = 0;   //写入了的长度
        private long contentLength = 0;  //总字节长度，避免多次调用contentLength()方法
        private long lastRefreshUiTime;  //最后一次刷新的时间
        private long refreshPer = 100;   //每隔多少时间回调一次

        public CountingSink(Sink delegate) {
            this(delegate,100);
        }

        public CountingSink(Sink delegate, long refreshPer) {
            super(delegate);
            this.refreshPer = refreshPer;
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            if (contentLength <= 0) contentLength = contentLength(); //获得contentLength的值，后续不再调用
            //增加当前写入的字节数
            bytesWritten += byteCount;

            long curTime = System.currentTimeMillis();
            //每100毫秒刷新一次数据,防止频繁无用的刷新
            if (curTime - lastRefreshUiTime >= refreshPer || bytesWritten == contentLength) {
                progressCallBack.onResponseProgress(bytesWritten, contentLength, bytesWritten == contentLength);
                lastRefreshUiTime = System.currentTimeMillis();
            }
            PrintLog.i("bytesWritten=" + bytesWritten + " ,totalBytesCount=" + contentLength);
        }
    }

}
