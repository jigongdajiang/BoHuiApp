package com.jimeijf.financing.start.login.result;

import java.io.Serializable;

/**
 * @author : gongdaocai
 * @date : 2017/11/10
 * FileName:
 * @description:
 */


public class VerCodeResult implements Serializable{
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "VerCodeResult{" +
                "code=" + code +
                '}';
    }
}
