package com.bohui.art.bean.detail;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2017/12/23
 * @description:
 */


public class ShowreelBean implements Serializable {
    private String img;//集合封面
    private String name;//集合名称
    private long id;//集合id

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ArtJiItemBean{" +
                "img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
