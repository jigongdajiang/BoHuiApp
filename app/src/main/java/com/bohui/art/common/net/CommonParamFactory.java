package com.bohui.art.common.net;

import android.content.Context;
import android.os.Build;

import com.bohui.art.common.app.AppFuntion;
import com.framework.core.cache.core.CacheCoreFactory;
import com.framework.core.http.model.HttpParams;
import com.framework.core.http.model.ICommonParamFactory;
import com.framework.core.util.AppInfoUtil;
import com.framework.core.util.DeviceInfoUtil;
import com.framework.core.util.DisplayUtil;
import com.framework.core.util.NetWorkUtil;
import com.framework.core.util.StrOperationUtil;

import org.json.JSONObject;

/**
 * @author : gaojigong
 * @date : 2017/11/10
 * @description:
 */


public class CommonParamFactory implements ICommonParamFactory {
    public static String P_COM_KEY_H= "h";

    public CommonParamFactory() {
    }

    @Override
    public HttpParams createCommomParams(Context context) {
        HttpParams params = new HttpParams();
        String header = getHeader(context);
        params.put(P_COM_KEY_H,header);
        return params;
    }
    /**
     * 头部信息
     */
    private String getHeader(Context context) {
        JSONObject h = new JSONObject();
        try {
            h.put("token", AppFuntion.getToken());//是否加密
            h.put("version", AppInfoUtil.getAppVersionCode(context));
            h.put("system", "Android");
            h.put("pka", AppInfoUtil.getPackname(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return h.toString();
    }
}
