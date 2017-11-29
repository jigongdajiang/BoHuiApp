package com.jimeijf.financing.common.net;

import android.content.Context;
import android.os.Build;

import com.jimeijf.core.cache.core.CacheCoreFactory;
import com.jimeijf.core.http.model.HttpParams;
import com.jimeijf.core.http.model.ICommonParamFactory;
import com.jimeijf.core.util.AppInfoUtil;
import com.jimeijf.core.util.DeviceInfoUtil;
import com.jimeijf.core.util.DisplayUtil;
import com.jimeijf.core.util.NetWorkUtil;
import com.jimeijf.core.util.StrOperationUtil;

import org.json.JSONObject;

/**
 * @author : gaojigong
 * @date : 2017/11/10
 * @FileName:
 * @description:
 */


public class CommonParamFactory implements ICommonParamFactory {
    public static String P_COM_KEY_R = "r";
    public static String P_COM_KEY_X = "x";
    public static String P_COM_KEY_H= "h";
    public static String P_COM_KEY_S= "s";
    private boolean isEntrypt;

    public CommonParamFactory(boolean isEntrypt) {
        this.isEntrypt = isEntrypt;
    }

    public CommonParamFactory() {
    }

    @Override
    public HttpParams createCommomParams(Context context) {
        HttpParams params = new HttpParams();
        String rid = String.valueOf(System.currentTimeMillis());//获取当前时间
        params.put(P_COM_KEY_R, rid);
        String userAgent = getUserAgent(context);
        params.put(P_COM_KEY_X,userAgent);
        String header = getHeader(context);
        params.put(P_COM_KEY_H,header);
        String tip = "jimeijf";
        params.put(P_COM_KEY_S,tip);
        return params;
    }

    /**
     * 设备基本信息
     */
    private String getUserAgent(Context context) {
        JSONObject joUserAgent = new JSONObject();
        try {
            joUserAgent = new JSONObject();
            //站点 0ios 1Android
            joUserAgent.put("site", 0);
            //客户端标志
            joUserAgent.put("os", "Android");
            //版本号
            joUserAgent.put("app", AppInfoUtil.getAppVersionCode(context));
            //厂商
            joUserAgent.put("model", Build.MODEL == null ? "UNKNOWN" : Build.MODEL);
            //手机号
            joUserAgent.put("imei", DeviceInfoUtil.getImei(context));
            //屏幕宽度
            joUserAgent.put("width", DisplayUtil.getScreenWidth(context));
            //系统版本号
            joUserAgent.put("os_version", Build.VERSION.RELEASE == null ? "UNKNOWN" : Build.VERSION.RELEASE);
            //屏幕高度
            joUserAgent.put("height", DisplayUtil.getScreenHeight(context));
            //androidId
            joUserAgent.put("os_id", DeviceInfoUtil.getAndroidId(context));
            //品牌标志
            joUserAgent.put("brand", Build.BRAND == null ? "UNKNOWN" : Build.BRAND);
            //国际移动用户识别码
            joUserAgent.put("imsi", DeviceInfoUtil.getImsi(context));
            //唯一识别码
            joUserAgent.put("sid", DeviceInfoUtil.getUUID());
            //屏幕尺寸
            joUserAgent.put("dpi", DisplayUtil.getDensityDpi(context));
            //渠道号
            joUserAgent.put("channel", DeviceInfoUtil.getChannel(context));
            //包名
            joUserAgent.put("packge", AppInfoUtil.getPackname(context));
            //签名
            joUserAgent.put("sign", StrOperationUtil.md5(AppInfoUtil.getAppSignature(context, AppInfoUtil.getPackname(context))));
            //cid
            joUserAgent.put("cid", CacheCoreFactory.getPreferenceCache(context).load(String.class, "cid"));
            //用户id
            joUserAgent.put("uid", CacheCoreFactory.getPreferenceCache(context).load(String.class, "uid"));
            //网络类型
            int type = NetWorkUtil.getCurrentNetType(context);
            if (type == NetWorkUtil.NETWOKR_TYPE_MOBILE) {//gprs
                joUserAgent.put("operator", "gprs");//操作方式 gprs
                joUserAgent.put("ip", NetWorkUtil.getGPRSLocalIpAddress());//ip
            } else if (type == NetWorkUtil.NETWORK_TYPE_WIFI) {//wifi
                joUserAgent.put("operator", "wifi");//操作方式 wifi
                joUserAgent.put("ip", NetWorkUtil.getWIFILocalIpAdress(context));//ip
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return joUserAgent.toString();
    }

    /**
     * 头部信息
     */
    private String getHeader(Context context) {
        JSONObject h = new JSONObject();
        try {
            h.put("encrypted", isEntrypt);//是否加密
            h.put("ticket", CacheCoreFactory.getPreferenceCache(context).load(String.class,"ticket"));
            h.put("encryption", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return h.toString();
    }
}
