package com.bohui.art.bean.detail;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/1/30
 * @description:
 */


public class DesignerDetailBean {
    private List<CaseBean> opusList;
    private String mobile;//联系手机号
    private String photo;//头像
    private String experience;//经验(年)
    private String name;//艺术家姓名
    private List<String> good_at_style;//["古典", "建筑", "平面"],//擅长风格
    private int is_recommed;
    private List<String> good_at_field;//["古典", "建筑", "平面"],//擅长领域
    private String company;//所在公司
    private String tag;//个人标签
    private long did;//id
    private String introduction;//个人简介
    private int isfollow;//0未关注1已关注

    public List<CaseBean> getOpusList() {
        return opusList;
    }

    public void setOpusList(List<CaseBean> opusList) {
        this.opusList = opusList;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGood_at_style() {
        return good_at_style;
    }

    public void setGood_at_style(List<String> good_at_style) {
        this.good_at_style = good_at_style;
    }

    public int getIs_recommed() {
        return is_recommed;
    }

    public void setIs_recommed(int is_recommed) {
        this.is_recommed = is_recommed;
    }

    public List<String> getGood_at_field() {
        return good_at_field;
    }

    public void setGood_at_field(List<String> good_at_field) {
        this.good_at_field = good_at_field;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getDid() {
        return did;
    }

    public void setDid(long did) {
        this.did = did;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getIsfollow() {
        return isfollow;
    }

    public void setIsfollow(int isfollow) {
        this.isfollow = isfollow;
    }
}
