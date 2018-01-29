package com.bohui.art.mine.accountedit.mvp;

/**
 * @author : gaojigong
 * @date : 2018/1/27
 * @description:
 */


public class UserInfoEditParam {
    private long uid;
    private int sex;
    private String name;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
