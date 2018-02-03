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

package com.framework.core.http.exception;

import org.json.JSONObject;

/**
 * <p>描述：处理服务器异常</p>
 * 压根没响应时封装成改异常对象，这种一般是无网络，超时等情况下使用
 * 作者： zhouyou<br>
 * 日期： 2016/9/15 16:51 <br>
 * 版本： v1.0<br>
 */
public class ServerException extends RuntimeException {
    private int errCode;
    private String message;
    //错误时的异常data对象
    private JSONObject errorData;

    public ServerException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
        this.message = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getErrorData() {
        return errorData;
    }

    public void setErrorData(JSONObject errorData) {
        this.errorData = errorData;
    }
}