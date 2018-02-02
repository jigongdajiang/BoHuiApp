package com.bohui.art.bean.found;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/1/23
 * @description:
 */


public class ArtManItemBean {
    private int paintingNum;
    private int level;
    private String name;
    private String photo;
    private long aid;
    private int followNum;
    private List<ArtManItemArtBean> paintingList;
    private List<String> goodat;

    public int getPaintingNum() {
        return paintingNum;
    }

    public void setPaintingNum(int paintingNum) {
        this.paintingNum = paintingNum;
    }

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public int getFollowNum() {
        return followNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }

    public List<ArtManItemArtBean> getPaintingList() {
        return paintingList;
    }

    public void setPaintingList(List<ArtManItemArtBean> paintingList) {
        this.paintingList = paintingList;
    }

    public List<String> getGoodat() {
        return goodat;
    }

    public void setGoodat(List<String> goodat) {
        this.goodat = goodat;
    }
}
