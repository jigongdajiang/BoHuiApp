package com.bohui.art.bean.detail;

import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.home.art2.Art2Activity;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class CompanyDetailResult {
    private long id;
    private String name;
    private String logo;
    private int num;
    private int isAttention;
    private String des;
    private String detail;
    private String address;
    private List<ArtItemBean> artItemBeans;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(int isAttention) {
        this.isAttention = isAttention;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<ArtItemBean> getArtItemBeans() {
        return artItemBeans;
    }

    public void setArtItemBeans(List<ArtItemBean> artItemBeans) {
        this.artItemBeans = artItemBeans;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
