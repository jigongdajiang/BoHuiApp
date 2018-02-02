package com.bohui.art.bean.found;

/**
 * @author : gaojigong
 * @date : 2018/1/30
 * @description:
 */


public class DesignerItemBean {
    private String name;//设计师姓名
    private String photo;//设计师头像
    private int is_recommed;//0认证1已认证
    private String company;//公司
    private String experience;//经验年
    private long did;//设计师id
    private String good_at_style;//擅长风格
    private int count;//案例数量

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

    public int getIs_recommed() {
        return is_recommed;
    }

    public void setIs_recommed(int is_recommed) {
        this.is_recommed = is_recommed;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public long getDid() {
        return did;
    }

    public void setDid(long did) {
        this.did = did;
    }

    public String getGood_at_style() {
        return good_at_style;
    }

    public void setGood_at_style(String good_at_style) {
        this.good_at_style = good_at_style;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
