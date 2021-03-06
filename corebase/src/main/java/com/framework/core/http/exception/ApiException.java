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

import android.net.ParseException;

import com.framework.core.http.model.ApiResult;
import com.framework.core.log.PrintLog;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;


/**
 * <p>描述：统一处理了API异常错误</p>
 * 得到服务器的返回了，且解析出相应数据了
 * 这个应该属于业务层，因为不同的业务的错误码定义可能不一样
 * 作者： zhouyou<br>
 * 日期： 2016/12/15 16:50 <br>
 * 版本： v1.0<br>
 */
public class ApiException extends Exception {
    //对应HTTP的状态码
    private static final int BADREQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int METHOD_NOT_ALLOWED = 405;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    private final int code;
    private String displayMessage;
    //只用于强制更新
    private JSONObject errorData;

    public static final int UNKNOWN = 1000;
    public static final int PARSE_ERROR = 1001;
    public static final int FORCE_UPDATE = 20005;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.displayMessage = throwable.getMessage();
    }

    public ApiException(String msg, int code) {
        super(msg);
        this.code = code;
        this.displayMessage = msg;
    }

    /**
     * 获取错误码
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取描述信息
     * @return
     */
    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String msg) {
        this.displayMessage = msg + "(code:" + code + ")";
    }

    public JSONObject getErrorData() {
        return errorData;
    }

    public void setErrorData(JSONObject errorData) {
        this.errorData = errorData;
    }

    /**
     * 是否是正常code值
     * @param apiResult
     * @return
     */
    public static boolean isOk(ApiResult apiResult) {
        if (apiResult == null)
            return false;
        if (apiResult.isOk() /*|| ignoreSomeIssue(apiResult.getCode())*/)
            return true;
        else
            return false;
    }
    /**
     * 错误分发处理将请求中的错误进行分类
     * 整个网络请求抛出的异常都经过这里
     *
     * @param e
     * @return
     */
    public static ApiException handleException(Throwable e) {
        PrintLog.e("Exception", e.getMessage());
        ApiException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ApiException(httpException, httpException.code());
            ex.displayMessage = httpException.getMessage();
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException.getMessage(), resultException.getErrCode());
            ex.setErrorData(resultException.getErrorData());
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof JsonSerializer
                || e instanceof NotSerializableException
                || e instanceof ParseException) {
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.displayMessage = "解析错误";
        } else if (e instanceof ClassCastException) {
            ex = new ApiException(e, ERROR.CAST_ERROR);
            ex.displayMessage = "类型转换错误";
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ERROR.NETWORD_ERROR);
            ex.displayMessage = "网络连接失败，请检查网络";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException(e, ERROR.SSL_ERROR);
            ex.displayMessage = "证书验证失败";
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.displayMessage = "网络连接超时，请稍后重试";
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.displayMessage = "网络连接超时，请稍后重试";
        } else if (e instanceof UnknownHostException) {
            ex = new ApiException(e, ERROR.UNKNOWNHOST_ERROR);
            ex.displayMessage = "无法解析该域名";
        } else if (e instanceof NullPointerException) {
            ex = new ApiException(e, ERROR.NULLPOINTER_EXCEPTION);
            ex.displayMessage = "空指针";
        } else if (e instanceof ApiException) {
            ex = (ApiException) e;
        } else {
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.displayMessage = "未知错误" + e.getMessage();
        }
        PrintLog.e("ApiException", "code=" + ex.getCode() + "--displayMessage=" + ex.getDisplayMessage());
        return ex;
    }

    @Override
    public String getMessage() {
        return displayMessage;
    }

    /**
     * 约定异常非业务异常
     */
    public static class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 100000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = UNKNOWN + 1;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = PARSE_ERROR + 1;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = NETWORD_ERROR + 1;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = HTTP_ERROR + 1;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = SSL_ERROR + 1;

        /**
         * 调用错误
         */
        public static final int INVOKE_ERROR = TIMEOUT_ERROR + 1;
        /**
         * 类转换错误
         */
        public static final int CAST_ERROR = INVOKE_ERROR + 1;
        /**
         * 请求取消
         */
        public static final int REQUEST_CANCEL = CAST_ERROR + 1;
        /**
         * 未知主机错误
         */
        public static final int UNKNOWNHOST_ERROR = REQUEST_CANCEL + 1;

        /**
         * 空指针错误
         */
        public static final int NULLPOINTER_EXCEPTION = UNKNOWNHOST_ERROR + 1;

        /**
         * 服务端没有返回json
         */
        public static final int SERVER_NULL_JSON = NULLPOINTER_EXCEPTION + 1;

        /**
         * 服务端Json数据格式错误
         */
        public static final int SERVER_JSON_UNEXCEPT = SERVER_NULL_JSON + 1;

        /**
         * 数据解密出错
         */
        public static final int SERVER_UN_EXCRYPT_ERROR = SERVER_JSON_UNEXCEPT + 1;

        /**
         * 强制更新
         */
        public static final int SERVER_FORCE_UPDATE = SERVER_UN_EXCRYPT_ERROR + 1;

        /**
         * 程序内部错误
         */
        public static final int PROGRESS_ERROR = SERVER_FORCE_UPDATE + 1;
    }

    public static class SERVERERROR {
        /**
         * 后台系统错误
         */
        public static final int SERVER_SYSTEM_ERROR = 20000;
        /**
         * 异地登录，需要重新登录
         */
        public static final int FORCE_RE_LOGIN = 20001;

        /**
         * 站点升级中
         */
        public static final int STATE_UPDATING = 20002;
        /**
         * 数据统计中
         */
        public static final int DATA_IS_STATISTICS = 20003;
        /**
         * 强制更新
         */
        public static final int FORCE_UPDATE = 20004;
        /**
         * 会员被禁
         */
        public static final int USER_IS_FORBED = 20005;
        /**
         * 重新登录
         */
        public static final int RE_LOGIN = 20006;
        /**
         * 流标
         */
        public static final int PRODUCT_FLOW = 20007;
        /**
         * 签名失败
         */
        public static final int SIGNATURE_FAILURE = 40000;
        /**
         * 重复请求
         */
        public static final int REQUEST_AGING = 50000;
        /**
         * 频繁请求
         */
        public static final int REQUEST_MORE = 50001;

    }

    public static class HTTP_ERROR {
        //对应HTTP的状态码
        public static final int BADREQUEST = 400;
        public static final int UNAUTHORIZED = 401;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        public static final int METHOD_NOT_ALLOWED = 405;
        public static final int REQUEST_TIMEOUT = 408;
        public static final int INTERNAL_SERVER_ERROR = 500;
        public static final int BAD_GATEWAY = 502;
        public static final int SERVICE_UNAVAILABLE = 503;
        public static final int GATEWAY_TIMEOUT = 504;
        public static final int STOP_SERVICE = 999;
    }
}