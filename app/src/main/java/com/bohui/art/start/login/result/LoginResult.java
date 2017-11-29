package com.bohui.art.start.login.result;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2017/11/10
 * @description:
 */


public class LoginResult implements Serializable {
    private String mobile;//验证码
    private String ticket;//会员票据
    private String uid;//用户id
    private String code;//0未注册  1注册了

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "mobile='" + mobile + '\'' +
                ", ticket='" + ticket + '\'' +
                ", uid='" + uid + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public void setCode(String code) {
        this.code = code;
    }
}
