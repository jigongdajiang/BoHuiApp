package com.jimeijf.financing.common.net;

import android.content.Context;

import com.jimeijf.core.http.model.HttpParams;
import com.jimeijf.core.http.model.IParamConvert;
import com.jimeijf.core.log.PrintLog;
import com.jimeijf.core.security.AESUtils;
import com.jimeijf.core.security.P2PSecurityRSACoder;
import com.jimeijf.core.security.RSAUtils;
import com.jimeijf.core.util.StrOperationUtil;

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
    public static final String PARAM_B = "b";
    public static final String PARAM_D = "d";
    private boolean isEncrypt;
    private Context context;
    private CommonParamFactory commonParamFactory;

    public ParamConvert(Context context,boolean isEncrypt) {
        this.context = context;
        this.isEncrypt = isEncrypt;
        this.commonParamFactory = new CommonParamFactory(isEncrypt);
    }

    public ParamConvert() {
    }

    public void setEncrypt(boolean encrypt) {
        isEncrypt = encrypt;
    }

    @Override
    public String convertParams(HttpParams params) {
        params.put(commonParamFactory.createCommomParams(context));
        JSONObject joParam = new JSONObject();//最终的json对象
        try {
            //获取公共部分
            String rid = params.urlParamsMap.get(CommonParamFactory.P_COM_KEY_R);
            JSONObject joH = new JSONObject(params.urlParamsMap.get(CommonParamFactory.P_COM_KEY_H));//公共的H部分

            JSONObject joData = new JSONObject();//存储d部分
            JSONObject joB = new JSONObject();//存储d的b部分

            //组装公共的
            joParam.put(CommonParamFactory.P_COM_KEY_R,params.urlParamsMap.get(CommonParamFactory.P_COM_KEY_R));
            //先以JsonObject为准，否则会出现很多转义符
            joData.put(CommonParamFactory.P_COM_KEY_X,new JSONObject(params.urlParamsMap.get(CommonParamFactory.P_COM_KEY_X)));

            //根据params组装joB
            for (ConcurrentHashMap.Entry<String, String> entry : params.urlParamsMap.entrySet()) {
                if(filterKey(entry.getKey())){
                    joB.put(entry.getKey(),entry.getValue());
                }
            }
            //加密非加密处理
            if(isEncrypt){
                //AES加密d部分
                joData.put(PARAM_B, joB);
                // 获取公钥
                String rsaPubKey = P2PSecurityRSACoder.getRsaPubKeyStr("jimeijinfu.cer", context);
                //随机生成16为的key
                String aesKey = AESUtils.getRandomString(16);
                //加密b部分
                String requestStr = RSAUtils.encryptBASE64(AESUtils.encrypt(joData.toString().getBytes("UTF-8"), aesKey));
                requestStr = URLEncoder.encode(requestStr, "utf-8");
                // 加密AesKey
                byte[] aesEncryptKey = P2PSecurityRSACoder.encryptByPublicKey(aesKey.getBytes(), rsaPubKey);
                aesKey = RSAUtils.encryptBASE64(aesEncryptKey);
                joH.put("encryption", aesKey);
                //s部分
                String sString = rid + requestStr;
                joParam.put(CommonParamFactory.P_COM_KEY_H, joH);//先以JsonObject为准，否则会出现很多转义符
                joParam.put(PARAM_D, requestStr);
                joParam.put(CommonParamFactory.P_COM_KEY_S, StrOperationUtil.md5(sString));
            }else{
                joData.put(PARAM_B, joB);//先以JsonObject为准，否则会出现很多转义符
                joParam.put(CommonParamFactory.P_COM_KEY_H, joH);//先以JsonObject为准，否则会出现很多转义符
                joParam.put(PARAM_D, joData);
                joParam.put(CommonParamFactory.P_COM_KEY_S,params.urlParamsMap.get(CommonParamFactory.P_COM_KEY_S));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String paramsStr = joParam.toString();
        PrintLog.json("params",paramsStr);
        return paramsStr;
    }

    private boolean filterKey(String key) {
        return !CommonParamFactory.P_COM_KEY_H.equals(key)
                && !CommonParamFactory.P_COM_KEY_R.equals(key)
                && !CommonParamFactory.P_COM_KEY_S.equals(key)
                && !CommonParamFactory.P_COM_KEY_X.equals(key);
    }
}
