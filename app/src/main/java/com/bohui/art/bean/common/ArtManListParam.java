package com.bohui.art.bean.common;

/**
 * @author : gaojigong
 * @date : 2018/1/23
 * @description:
 */


public class ArtManListParam extends PageParam {
    private int level = 0;
    private String name="";
    private int oneid = 0;
    private int towid = 0;

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

    public int getOneid() {
        return oneid;
    }

    public void setOneid(int oneid) {
        this.oneid = oneid;
    }

    public int getTowid() {
        return towid;
    }

    public void setTowid(int towid) {
        this.towid = towid;
    }
}
