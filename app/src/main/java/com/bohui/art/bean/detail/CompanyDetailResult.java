package com.bohui.art.bean.detail;

import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.home.art2.Art2Activity;

import java.io.Serializable;
import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class CompanyDetailResult implements Serializable{
    private String address;
    private int num;
    private String name;
    private String mobile;
    private int mid;
    private String logo;
    private String detail;
    private int isfollow;//是否关注：0未关注1已关注
    private String desc;
    private List<ArtItemBean> list;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

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

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getIsfollow() {
        return isfollow;
    }

    public void setIsfollow(int isfollow) {
        this.isfollow = isfollow;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ArtItemBean> getList() {
        return list;
    }

    public void setList(List<ArtItemBean> list) {
        this.list = list;
    }
}
