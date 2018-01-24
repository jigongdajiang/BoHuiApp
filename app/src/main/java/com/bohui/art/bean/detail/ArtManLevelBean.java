package com.bohui.art.bean.detail;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2018/1/20
 * @description:
 */


public class ArtManLevelBean implements Serializable {
    private int id;
    private String name;

    public ArtManLevelBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
