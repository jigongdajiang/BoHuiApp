package com.bohui.art.bean.found;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class CompanyListItemBean implements Serializable{
    private int mid;
    private String name;
    private int num;
    private String logo;

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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
