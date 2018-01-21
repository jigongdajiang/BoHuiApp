package com.bohui.art.bean.common;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class BannerBean implements Serializable{
    private long id;
    private String image;
    private String name;
    private String url;

    public BannerBean() {
    }

    public BannerBean(String image, String name, String url) {
        this.image = image;
        this.name = name;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
