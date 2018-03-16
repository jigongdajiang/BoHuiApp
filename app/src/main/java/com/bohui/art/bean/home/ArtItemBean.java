package com.bohui.art.bean.home;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2018/1/20
 * @description:
 * 艺术品横向列表数据信息
 */


public class ArtItemBean implements Serializable {
    private long id;
    private long aid;//有时候是aid
    private String name;
    private String cover;
    private String size;
    private int lookNum;
    private double saleprice;
    private double price;

    public ArtItemBean() {
    }

    public ArtItemBean(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getLookNum() {
        return lookNum;
    }

    public void setLookNum(int lookNum) {
        this.lookNum = lookNum;
    }

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ArtItemBean{" +
                "id=" + id +
                ", aid=" + aid +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", size='" + size + '\'' +
                ", lookNum=" + lookNum +
                ", salePrice=" + saleprice +
                ", price=" + price +
                '}';
    }
}
