package com.bohui.art.bean.detail;

import com.bohui.art.bean.home.ArtCoverItemBean;
import com.bohui.art.bean.home.ArtItemBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/23
 * @description:
 */


public class ArtMainDetailResult {
    private List<ArtItemBean> dlist;//代表作品集合
    private List<ShowreelBean> setlist;//作品集合
    private String personalnote;//个人说明
    private int follownum;//粉丝量
    private int level;//级别 1国家2省3市4其他
    private int num;//总作品数
    private String name;//艺术家姓名
    private String photo;//头像
    private String artistic;//艺术语言
    private String detail;//个人详情
    private String leveldesc;//级别说明
    private long aid;//用户ID
    private int isfollow;//0未关注1已关注

    public List<ArtItemBean> getDlist() {
        return dlist;
    }

    public void setDlist(List<ArtItemBean> dlist) {
        this.dlist = dlist;
    }

    public List<ShowreelBean> getSetlist() {
        return setlist;
    }

    public void setSetlist(List<ShowreelBean> setlist) {
        this.setlist = setlist;
    }

    public String getPersonalnote() {
        return personalnote;
    }

    public void setPersonalnote(String personalnote) {
        this.personalnote = personalnote;
    }

    public int getFollownum() {
        return follownum;
    }

    public void setFollownum(int follownum) {
        this.follownum = follownum;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public String getArtistic() {
        return artistic;
    }

    public void setArtistic(String artistic) {
        this.artistic = artistic;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLeveldesc() {
        return leveldesc;
    }

    public void setLeveldesc(String leveldesc) {
        this.leveldesc = leveldesc;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public int getIsfollow() {
        return isfollow;
    }

    public void setIsfollow(int isfollow) {
        this.isfollow = isfollow;
    }

    @Override
    public String toString() {
        return "ArtMainDetailResult{" +
                "dlist=" + dlist +
                ", setlist=" + setlist +
                ", personalnote='" + personalnote + '\'' +
                ", follownum=" + follownum +
                ", level=" + level +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", artistic='" + artistic + '\'' +
                ", detail='" + detail + '\'' +
                ", leveldesc='" + leveldesc + '\'' +
                ", aid=" + aid +
                '}';
    }
}
