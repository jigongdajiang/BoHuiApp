package com.jimeijf.core.cache.core.preference;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @author : gaojigong
 * @date : 2017/11/14
 * @FileName:
 * @description:
 */


public class GsonObjectStrategy extends AbsObjectStrategy {
    @Override
    public String save(Object object) {
        String str = null;
        try {
            //将对象转为json串
            Gson gson = new Gson();
            String objJsonStr = gson.toJson(object);
            //将json串进行转换为密文
            str = bytesToHexString(objJsonStr.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    @Override
    public Object read(String str, Type type) {
        Object value = null;
        //采用Gson不用所有的对象都要集成序列化标志
        if (!TextUtils.isEmpty(str)) {
            //将16进制的数据转为数组，准备反序列化
            byte[] stringToBytes = StringToBytes(str);
            String jsonStr = new String(stringToBytes);
            Gson gson = new Gson();
            value = gson.fromJson(jsonStr, type);
        }
        return value;
    }
}
