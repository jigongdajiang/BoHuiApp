package com.jimeijf.core.cache.core.preference;

import com.jimeijf.core.util.CUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;

/**
 * @author : gaojigong
 * @date : 2017/11/14
 * @description:
 */


public class SerializableObjectStrategy extends AbsObjectStrategy {
    @Override
    public String save(Object object) {
        //先将序列化结果写到byte缓存中，其实就分配一个内存空间
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(object);
            //将序列化的数据转为16进制保存
            String bytesString = bytesToHexString(bos.toByteArray());
            return bytesString;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CUtil.close(os);
        }
        return null;
    }

    @Override
    public Object read(String str, Type type) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(str.getBytes());
            ObjectInputStream is = new ObjectInputStream(bis);
            //返回反序列化得到的对象
            Object value = is.readObject();
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
