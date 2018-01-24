package com.bohui.art.bean.common;

/**
 * @author : gaojigong
 * @date : 2018/1/23
 * @description:
 */


public class ArtManListParam extends PageParam {
    private int level;
    private String name="";

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
