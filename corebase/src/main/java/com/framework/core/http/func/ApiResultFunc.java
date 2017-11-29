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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.framework.core.http.exception.ApiException;
import com.framework.core.http.model.ApiResult;
import com.framework.core.log.PrintLog;
import com.framework.core.security.AESUtils;
import com.framework.core.security.P2PSecurityRSACoder;
import com.framework.core.security.RSAUtils;

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

    public ApiResultFunc(Type type,Context context) {
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
            int state = jsonObject.optInt("state");
            String encrypted = jsonObject.optString("encrypted", "");
            String encryption = jsonObject.optString("encryption", "");
            JSONObject returnData;
            if ("true".equals(encrypted)) {
                String data = jsonObject.optString("data");
                byte[] deaescryptKey = P2PSecurityRSACoder.decryptByPublicKey(RSAUtils.decryptBASE64(encryption), PUBLIC_FILE_NAME, context);
                String aesKey = new String(deaescryptKey, "utf-8");
                String reponseData = new String(AESUtils.decrypt(RSAUtils.decryptBASE64(data), aesKey));
                returnData = new JSONObject(reponseData);
            } else {
                returnData = jsonObject.optJSONObject("data");
            }
            jsonObject.remove("data");
            jsonObject.put("data", returnData);
            if (state == ApiException.FORCE_UPDATE) {
                ApiException exception = new ApiException("强制更新", ApiException.ERROR.SERVER_FORCE_UPDATE);
                exception.setExtMessage(returnData.toString());
                throw exception;
            } else if (state == ApiResult.OK) {
                String jsonStr = jsonObject.toString();
                ApiResult result = gson.fromJson(jsonStr, type);
                if (result != null) {
                    apiResult = result;
                } else {
                    throw new JsonParseException("return ok but fromJson failed");
                }
            } else {
                String msg = jsonObject.optString("message");
                ApiException exception = new ApiException(msg, state);
                throw exception;
            }
        } else {
            throw new ApiException("服务端返回数据为空", ApiException.ERROR.SERVER_NULL_JSON);
        }
        return apiResult;
    }
}
