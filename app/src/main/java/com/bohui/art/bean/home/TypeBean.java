package com.bohui.art.bean.home;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class TypeBean implements Serializable{
    private int typeId;
    private String type;

    public TypeBean(int typeId, String type) {
        this.typeId = typeId;
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
