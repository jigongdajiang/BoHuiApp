package com.bohui.art.common.net;

import android.content.Context;

import com.framework.core.http.model.HttpParams;
import com.framework.core.http.model.IParamConvert;
import com.framework.core.log.PrintLog;
import com.framework.core.security.AESUtils;
import com.framework.core.security.P2PSecurityRSACoder;
import com.framework.core.security.RSAUtils;
import com.framework.core.util.StrOperationUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : gaojigong
 * @date : 2017/11/10
 * @description:
 */


public class ParamConvert implements IParamConvert<String> {
    public static final String PARAM_H = "heard";
    public static final String PARAM_B = "param";
    private Context context;
    private CommonParamFactory commonParamFactory;

    public ParamConvert(Context context) {
        this.context = context;
        this.commonParamFactory = new CommonParamFactory();
    }

    public ParamConvert() {
    }


    @Override
    public String convertParams(HttpParams params) {
        params.put(commonParamFactory.createCommomParams(context));
        JSONObject joParam = new JSONObject();//最终的json对象
        try {
            //获取公共部分
            JSONObject joH = new JSONObject(params.urlParamsMap.get(CommonParamFactory.P_COM_KEY_H));//公共的H部分

            JSONObject joB = new JSONObject();//存储d的b部分

            //根据params组装joB
            for (ConcurrentHashMap.Entry<String, String> entry : params.urlParamsMap.entrySet()) {
                if(filterKey(entry.getKey())){
                    joB.put(entry.getKey(),entry.getValue());
                }
            }
            joParam.put(PARAM_H, joH);
            joParam.put(PARAM_B, joB);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String paramsStr = joParam.toString();
        PrintLog.json("params",paramsStr);
        return paramsStr;
    }

    private boolean filterKey(String key) {
        return !CommonParamFactory.P_COM_KEY_H.equals(key);
    }
}
