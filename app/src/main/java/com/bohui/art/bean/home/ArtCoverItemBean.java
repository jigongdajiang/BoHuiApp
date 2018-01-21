package com.bohui.art.bean.home;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 * 艺术品推荐封面型数据
 */


public class ArtCoverItemBean implements Serializable{
    private String cover;
    private String name;
    private long id;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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
        return "ArtCoverItemBean{" +
                "cover='" + cover + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
