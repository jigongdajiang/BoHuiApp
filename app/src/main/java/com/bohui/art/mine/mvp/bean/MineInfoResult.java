package com.bohui.art.mine.mvp.bean;

/**
 * @author : gaojigong
 * @date : 2017/12/23
 * @description:
 */


public class MineInfoResult {
    private String name;
    private String mobile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "MineInfoResult{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
