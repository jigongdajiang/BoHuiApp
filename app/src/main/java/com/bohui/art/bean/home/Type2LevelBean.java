package com.bohui.art.bean.home;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2017/12/11
 * @description:
 */


public class Type2LevelBean implements Serializable{
    private String imgUrl;
    private String type;
    private int typeId;

    public Type2LevelBean(String imgUrl, String type, int typeId) {
        this.imgUrl = imgUrl;
        this.type = type;
        this.typeId = typeId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
