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

package com.framework.core.http.func;

import android.content.Context;
import android.text.TextUtils;

import com.framework.core.http.exception.ApiException;
import com.framework.core.http.model.ApiResult;
import com.framework.core.log.PrintLog;
import com.framework.core.security.AESUtils;
import com.framework.core.security.P2PSecurityRSACoder;
import com.framework.core.security.RSAUtils;
import com.framework.core.util.TUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;


/**
 * <p>描述：定义了ApiResult结果转换Func</p>
 * 将ResponseBody转为ApiResult
 * 这里对应不同的业务，转义规则可能不一样，需要后期抽象出来<T>
 * 作者： zhouyou<br>
 * 日期： 2017/3/15 16:52 <br>
 * 版本： v1.0<br>
 */
public class ApiResultFunc<T> implements Function<ResponseBody, ApiResult<T>> {
    //加密证书
    public static final String PUBLIC_FILE_NAME = "jimeijinfu.cer";

    protected Type type;
    protected Gson gson;
    protected Context context;

    public ApiResultFunc(Type type, Context context) {
        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();
        this.type = type;
        this.context = context;
    }

    @Override
    public ApiResult<T> apply(@NonNull ResponseBody responseBody) throws Exception {
        //这里是最原始的json
        //强制更新时为一个强制更新的数据结构
        //加密时为一个字符串需要解密后重组
        //正常是才为指定的类型
        String json = responseBody.string();
        PrintLog.json("responseBody",json);
        responseBody.close();
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(-1);
        if (!TextUtils.isEmpty(json)) {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.optInt("code");
            String message = jsonObject.optString("msg");
            apiResult.setCode(code);
            apiResult.setMsg(message);
            JSONObject returnData = jsonObject.optJSONObject("data");
            jsonObject.put("data", returnData);
            String jsonStr = jsonObject.toString();
            if (code == ApiException.FORCE_UPDATE) {
                ApiException exception = new ApiException("强制更新", ApiException.ERROR.SERVER_FORCE_UPDATE);
                exception.setErrorData(returnData);
                throw exception;
            } else if (code == ApiResult.OK) {
                final Class<T> clazz = TUtil.getClass(type, 0);
                if (clazz.equals(String.class)) {
                    //data 可能同一个接口有多种类型，这时候把Data泛型为String，到返回层再去解析
                    apiResult.setCode(code);
                    apiResult.setMsg(message);
                    apiResult.setData((T) returnData.toString());
                } else {
                    try {
                        ApiResult result = gson.fromJson(jsonStr, type);
                        result.setCode(code);
                        result.setMsg(message);
                        apiResult = result;
                    } catch (Exception e) {
                        PrintLog.e("JsonParseException",e.getMessage());
                        throw new JsonParseException("return ok but fromJson failed");
                    }
                }
            }else {
                //服务业务逻辑错误
                apiResult.setCode(code);
                apiResult.setMsg(message);
                apiResult.setErrrorData(returnData);
            }
        } else {
            throw new ApiException("服务端返回数据为空", ApiException.ERROR.SERVER_NULL_JSON);
        }
        return apiResult;
    }
}
