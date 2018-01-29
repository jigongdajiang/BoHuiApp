package com.bohui.art.bean.mine;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2017/12/23
 * @description:
 */


public class MineInfoResult implements Serializable{
    private long uid;
    private int sex;
    private String name;
    private String mobile;
    private String photo;
    private int age;
    private String industry;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "MineInfoResult{" +
                "uid=" + uid +
                ", sex=" + sex +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", photo='" + photo + '\'' +
                ", age=" + age +
                ", industry='" + industry + '\'' +
                '}';
    }
}
