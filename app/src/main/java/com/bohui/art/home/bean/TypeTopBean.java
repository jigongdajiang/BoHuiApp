package com.bohui.art.home.bean;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class TypeTopBean implements Serializable{
    private String des;
    private int typeId;
    private int grade;

    public TypeTopBean(String des, int typeId) {
        this.des = des;
        this.typeId = typeId;
    }

    public TypeTopBean(String des, int typeId, int grade) {
        this.des = des;
        this.typeId = typeId;
        this.grade = grade;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
