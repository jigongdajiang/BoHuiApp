package com.bohui.art.bean.found;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtManLevelBean implements Serializable{

    /**
     * name : 二级分类名称
     * tid : 二级分类id
     */

    private String name;
    private int tid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }
}
