package com.bohui.art.bean.common;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class BannerBean implements Serializable{
    private String title;
    private String url;
    private String imgUrl;

    public BannerBean() {
    }

    public BannerBean(String title, String url, String imgUrl) {
        this.title = title;
        this.url = url;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
