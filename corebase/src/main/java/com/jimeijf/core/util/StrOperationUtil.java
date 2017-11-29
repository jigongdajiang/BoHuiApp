package com.jimeijf.core.util;

import java.security.MessageDigest;

/**
 * @author : gaojigong
 * @date : 2017/11/17
 * @FileName:
 * @description:
 * 字符串操作类
 * md5操作
 * 判空操作等
 *
 */


public class StrOperationUtil {
    public static boolean isEmpty(String str){
        return str == null || str.length() == 0 || str.equals("null");
    }
    /***
     * MD5
     */
    public static String md5(String paramString) {
        String returnStr;
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            returnStr = byteToHexString(localMessageDigest.digest());
            return returnStr;
        } catch (Exception e) {
            return paramString;
        }
    }
    /**
     * 将指定byte数组转换成16进制字符串
     * 小写
     */
    private static String byteToHexString(byte[] b) {
        StringBuilder hexString = new StringBuilder();
        for (byte aB : b) {
            String hex = Integer.toHexString(aB & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toLowerCase());
        }
        return hexString.toString();
    }
}
