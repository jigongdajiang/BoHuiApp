package com.jimeijf.financing.common.bean;


import java.io.Serializable;

/**
 * @author hujiajun
 * @version V1.0
 * @Description: 首页
 * @date 17/02/13.
 */
public class HomeLinkBean implements Serializable {
    private String action;//推送跳转动作
    private String title;//标题
    private String url;//静态页面的url
    private String cover;//图片地址
    private String tagLine;//内容
    private String skipType;//类型1表示URL链接，2应用内跳转，3无动作
    private String imageUid;//图片id
    private String icon;//图片id
    private String shareable;//是否可以分享
    private String shareIcon;//分享icon
    private String shareTitle;//分享标题
    private String shareContext;//分享内容
    private String isClosed;//图片是否关闭 1：图片可关闭 0：图片不可关闭

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getImageUid() {
        return imageUid;
    }

    public void setImageUid(String imageUid) {
        this.imageUid = imageUid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getShareable() {
        return shareable;
    }

    public void setShareable(String shareable) {
        this.shareable = shareable;
    }

    public String getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(String shareIcon) {
        this.shareIcon = shareIcon;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareContext() {
        return shareContext;
    }

    public void setShareContext(String shareContext) {
        this.shareContext = shareContext;
    }

    public String getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
    }

    @Override
    public String toString() {
        return "HomeLink{" +
                "action='" + action + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", cover='" + cover + '\'' +
                ", tagLine='" + tagLine + '\'' +
                ", skipType='" + skipType + '\'' +
                ", imageUid='" + imageUid + '\'' +
                ", icon='" + icon + '\'' +
                ", shareable='" + shareable + '\'' +
                ", shareIcon='" + shareIcon + '\'' +
                ", shareTitle='" + shareTitle + '\'' +
                ", shareContext='" + shareContext + '\'' +
                ", isClosed='" + isClosed + '\'' +
                '}';
    }
}