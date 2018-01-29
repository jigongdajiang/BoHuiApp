package com.bohui.art.bean.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/1/21
 * @description:
 */


public class ArtListParam extends PageParam {
    private List<Long> oneclass=new ArrayList<>();
    private List<Long> towclass=new ArrayList<>();
    private String name;
    private double startprice;
    private double endprice;
    private int level;
    private int pricesort;
    private int looksort;
    private int youxian;

    public List<Long> getOneclass() {
        return oneclass;
    }

    public void setOneclass(List<Long> oneclass) {
        this.oneclass = oneclass;
    }

    public List<Long> getTowclass() {
        return towclass;
    }

    public void setTowclass(List<Long> towclass) {
        this.towclass = towclass;
    }

    public double getStartprice() {
        return startprice;
    }

    public void setStartprice(double startprice) {
        this.startprice = startprice;
    }

    public double getEndprice() {
        return endprice;
    }

    public void setEndprice(double endprice) {
        this.endprice = endprice;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPricesort() {
        return pricesort;
    }

    public void setPricesort(int pricesort) {
        this.pricesort = pricesort;
    }

    public int getLooksort() {
        return looksort;
    }

    public void setLooksort(int looksort) {
        this.looksort = looksort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYouxian() {
        return youxian;
    }

    public void setYouxian(int youxian) {
        this.youxian = youxian;
    }

    @Override
    public String toString() {
        return "ArtListParam{" +
                "oneclass=" + oneclass.toString() +
                ", towclass=" + towclass.toString() +
                ", name='" + name + '\'' +
                ", startprice=" + startprice +
                ", endprice=" + endprice +
                ", level=" + level +
                ", pricesort=" + pricesort +
                ", looksort=" + looksort +
                ", youxian=" + youxian +
                '}';
    }
}
