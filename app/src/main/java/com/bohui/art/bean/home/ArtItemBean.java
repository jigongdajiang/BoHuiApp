package com.bohui.art.bean.home;

/**
 * @author : gaojigong
 * @date : 2018/1/20
 * @description:
 * 艺术品横向列表数据信息
 */


public class ArtItemBean {
    private long id;
    private String name;
    private String cover;
    private String size;
    private int lookNum;
    private double salePrice;

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

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "ArtItemBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", size='" + size + '\'' +
                ", lookNum=" + lookNum +
                ", salePrice=" + salePrice +
                '}';
    }
}
