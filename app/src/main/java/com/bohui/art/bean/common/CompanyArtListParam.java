package com.bohui.art.bean.common;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public class CompanyArtListParam extends PageParam {
    private int mid;
    private String name;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
