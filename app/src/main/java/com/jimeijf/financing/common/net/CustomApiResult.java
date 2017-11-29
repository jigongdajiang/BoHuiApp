package com.jimeijf.financing.common.net;

import com.jimeijf.core.http.model.ApiResult;

/**
 * @author : gongdaocai
 * @date : 2017/11/10
 * FileName:
 * @description:
 */


public class CustomApiResult<T> extends ApiResult<T> {
    private String encrypted;
    private String encryption;
    private String interval;
    private String message;
    private String rid;
    private int state;
    public String getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public int getCode() {
        return getState();
    }

    @Override
    public boolean isOk() {
        return getCode() == OK;
    }

    @Override
    public String toString() {
        return "JMApiResult{" +
                "encrypted='" + encrypted + '\'' +
                ", encryption='" + encryption + '\'' +
                ", interval='" + interval + '\'' +
                ", message='" + message + '\'' +
                ", rid='" + rid + '\'' +
                ", state=" + state +
                '}';
    }
}
