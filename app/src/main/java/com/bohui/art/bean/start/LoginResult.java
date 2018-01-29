package com.bohui.art.bean.start;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public class LoginResult implements Serializable{
    private long uid;
    private int sex;
    private String name;
    private String mobile;
    private String photo;
    private int is;
    private int age;
    private String token;

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

    public int getIs() {
        return is;
    }

    public void setIs(int is) {
        this.is = is;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "uid=" + uid +
                ", sex=" + sex +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", photo='" + photo + '\'' +
                ", is=" + is +
                ", age=" + age +
                ", token='" + token + '\'' +
                '}';
    }
}
