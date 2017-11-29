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

package com.jimeijf.core.http.model;

import com.jimeijf.core.http.callback.ProgressResponseCallBack;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.MediaType;

/**
 * <p>描述：对普通参数操作的封装</p>
 * 1.普通键值对操作
 * 2.文件键值对操作
 * 作者： zhouyou<br>
 * 日期： 2017/5/2 16:58 <br>
 * 版本： v1.0<br>
 */
public class HttpParams implements Serializable {
    /**
     * 普通的键值对参数
     */
    public LinkedHashMap<String, String> urlParamsMap;
    /**
     * 文件的键值对参数
     */
    public LinkedHashMap<String, List<FileWrapper>> fileParamsMap;

    public HttpParams() {
        init();
    }

    public HttpParams(String key, String value) {
        init();
        put(key, value);
    }

    private void init() {
        urlParamsMap = new LinkedHashMap<>();
        fileParamsMap = new LinkedHashMap<>();
    }

    /**
     * 原型拷贝
     */
    public void put(HttpParams params) {
        if (params != null) {
            if (params.urlParamsMap != null && !params.urlParamsMap.isEmpty())
                urlParamsMap.putAll(params.urlParamsMap);

            if (params.fileParamsMap != null && !params.fileParamsMap.isEmpty()) {
                fileParamsMap.putAll(params.fileParamsMap);
            }
        }
    }

    /**
     * 普通多参数设置
     */
    public void put(Map<String, String> params) {
        if (params == null || params.isEmpty()) return;
        urlParamsMap.putAll(params);
    }

    /**
     * 普通单参数设置
     */
    public void put(String key, String value) {
        urlParamsMap.put(key, value);
    }

    public <T extends File> void put(String key, T file, ProgressResponseCallBack responseCallBack) {
        put(key, file, file.getName(), responseCallBack);
    }

    public <T extends File> void put(String key, T file, String fileName, ProgressResponseCallBack responseCallBack) {
        put(key, file, fileName, guessMimeType(fileName), responseCallBack);
    }

    public <T extends InputStream> void put(String key, T file, String fileName, ProgressResponseCallBack responseCallBack) {
        put(key, file, fileName, guessMimeType(fileName), responseCallBack);
    }

    public void put(String key, byte[] bytes, String fileName, ProgressResponseCallBack responseCallBack) {
        put(key, bytes, fileName, guessMimeType(fileName), responseCallBack);
    }

    public void put(String key, FileWrapper fileWrapper) {
        if (key != null && fileWrapper != null) {
            put(key, fileWrapper.file, fileWrapper.fileName, fileWrapper.contentType, fileWrapper.responseCallBack);
        }
    }

    /**
     * 单文件参数设置
     * @param key key值
     * @param countent 具体文件File或者是byte[]
     * @param fileName 文件名
     * @param contentType type
     * @param responseCallBack 上传回调
     * @param <T>
     */
    public <T> void put(String key, T countent, String fileName, MediaType contentType, ProgressResponseCallBack responseCallBack) {
        if (key != null) {
            List<FileWrapper> fileWrappers = fileParamsMap.get(key);
            if (fileWrappers == null) {
                fileWrappers = new ArrayList<>();
                fileParamsMap.put(key, fileWrappers);
            }
            fileWrappers.add(new FileWrapper(countent, fileName, contentType, responseCallBack));
        }
    }

    /**
     * 多文件设置
     */
    public <T extends File> void putFileParams(String key, List<T> files, ProgressResponseCallBack responseCallBack) {
        if (key != null && files != null && !files.isEmpty()) {
            for (File file : files) {
                put(key, file, responseCallBack);
            }
        }
    }

    /**
     * 原型赋值
     */
    public void putFileWrapperParams(String key, List<FileWrapper> fileWrappers) {
        if (key != null && fileWrappers != null && !fileWrappers.isEmpty()) {
            for (FileWrapper fileWrapper : fileWrappers) {
                put(key, fileWrapper);
            }
        }
    }

    /**
     * 清除普通单参数
     */
    public void removeUrl(String key) {
        urlParamsMap.remove(key);
    }

    /**
     * 清除文件单参数
     */
    public void removeFile(String key) {
        fileParamsMap.remove(key);
    }

    /**
     * 清除同key参数，包含文件和普通
     */
    public void remove(String key) {
        removeUrl(key);
        removeFile(key);
    }

    /**
     * 清空参数
     */
    public void clear() {
        urlParamsMap.clear();
        fileParamsMap.clear();
    }

    /**
     * 猜测文件网络路径对应的MediaType
     */
    private MediaType guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        path = path.replace("#", "");   //解决文件名中含有#号异常的问题
        String contentType = fileNameMap.getContentTypeFor(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return MediaType.parse(contentType);
    }

    /**
     * 文件类型的包装类
     */
    public static class FileWrapper<T> {
        public T file;//可以是byte[]或File类型
        public String fileName;
        public MediaType contentType;
        public long fileSize;
        public ProgressResponseCallBack responseCallBack;

        public FileWrapper(T file, String fileName, MediaType contentType, ProgressResponseCallBack responseCallBack) {
            this.file = file;
            this.fileName = fileName;
            this.contentType = contentType;
            if (file instanceof File) {
                this.fileSize = ((File) file).length();
            } else if (file instanceof byte[]) {
                this.fileSize = ((byte[]) file).length;
            }
            this.responseCallBack = responseCallBack;
        }

        @Override
        public String toString() {
            return "FileWrapper{" + "countent=" + file + ", fileName='" + fileName + ", contentType=" + contentType + ", fileSize=" + fileSize + '}';
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : urlParamsMap.entrySet()) {
            if (result.length() > 0) result.append("&");
            result.append(entry.getKey()).append("=").append(entry.getValue());
        }
        for (ConcurrentHashMap.Entry<String, List<FileWrapper>> entry : fileParamsMap.entrySet()) {
            if (result.length() > 0) result.append("&");
            result.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return result.toString();
    }
}