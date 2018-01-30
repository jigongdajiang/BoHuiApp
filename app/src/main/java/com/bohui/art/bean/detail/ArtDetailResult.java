package com.bohui.art.bean.detail;

import com.bohui.art.bean.home.ArtItemBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/20
 * @description:
 */


public class ArtDetailResult {
    private List<String> imgs;
    private String size;// "12*34",//尺寸
    private String artname;// "柏树停",//作者名称
    private int    level;// 1,//作者级别 1国家2省3市
    private double price;// 100000.00,//价格
    private String name;//梦之蓝",//作品名称
    private double saleprice;//: 9999.00,//促销价
    private String photo;// "http://bohuiyishu.oss-cn-beijing.aliyuncs.com/file/1523863942682855.jpg",//作者头像
    private long   pid;// 17,//作品id
    private String detailH5;// "http://bohuiyishu?id=17",//详情H5链接地址
    private int    looknum;// 0,//浏览数
    private String desc;// "梦中之蓝"//简介
    private long aid;//艺术家id
    private List<ArtItemBean> recommendList;//同类艺术品推荐
    private int isfollow;//0未收藏1已收藏


    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getArtname() {
        return artname;
    }

    public void setArtname(String artname) {
        this.artname = artname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getDetailH5() {
        return detailH5;
    }

    public void setDetailH5(String detailH5) {
        this.detailH5 = detailH5;
    }

    public int getLooknum() {
        return looknum;
    }

    public void setLooknum(int looknum) {
        this.looknum = looknum;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public List<ArtItemBean> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<ArtItemBean> recommendList) {
        this.recommendList = recommendList;
    }

    public int getIsfollow() {
        return isfollow;
    }

    public void setIsfollow(int isfollow) {
        this.isfollow = isfollow;
    }
}
