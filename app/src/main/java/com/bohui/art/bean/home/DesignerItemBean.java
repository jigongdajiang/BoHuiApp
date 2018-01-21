package com.bohui.art.bean.home;

/**
 * @author : gaojigong
 * @date : 2018/1/20
 * @description:
 */


public class DesignerItemBean {
    private String cover;
    private String name;
    private String experience;
    private String storefront;
    private String good_at_field;
    private String case_one;
    private String good_at_style;
    private String did;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getStorefront() {
        return storefront;
    }

    public void setStorefront(String storefront) {
        this.storefront = storefront;
    }

    public String getGood_at_field() {
        return good_at_field;
    }

    public void setGood_at_field(String good_at_field) {
        this.good_at_field = good_at_field;
    }

    public String getCase_one() {
        return case_one;
    }

    public void setCase_one(String case_one) {
        this.case_one = case_one;
    }

    public String getGood_at_style() {
        return good_at_style;
    }

    public void setGood_at_style(String good_at_style) {
        this.good_at_style = good_at_style;
    }

    @Override
    public String toString() {
        return "DesignerItemBean{" +
                "cover='" + cover + '\'' +
                ", name='" + name + '\'' +
                ", experience='" + experience + '\'' +
                ", storefront='" + storefront + '\'' +
                ", good_at_field='" + good_at_field + '\'' +
                ", case_one='" + case_one + '\'' +
                ", good_at_style='" + good_at_style + '\'' +
                ", did='" + did + '\'' +
                '}';
    }
}
