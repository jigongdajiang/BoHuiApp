package com.bohui.art.bean.found;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class ArtManHomeItemBean implements Serializable{
    private String name;
    private String photo;
    private long aid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }
}
