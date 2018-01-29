package com.bohui.art.bean.found;

/**
 * @author : gaojigong
 * @date : 2018/1/27
 * @description:
 */


public class OrderBean {
    private String type;
    private String price;
    private String size;
    private String num;
    private String remarks;
    private String mobile;
    private String name;
    private long uid;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "OrderParam{" +
                "type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", size='" + size + '\'' +
                ", num='" + num + '\'' +
                ", remarks='" + remarks + '\'' +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", uid=" + uid +
                '}';
    }
}
