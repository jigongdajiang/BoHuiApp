package com.jimeijf.financing.common.rv.bean;

/**
 * @author gaojigong
 * @version V1.0
 * @Description: 无数据实体类
 * @date 16/11/14.
 */
public class EmptyBean {
    private boolean isShow;
    private boolean isLogoShow;//是否显示logo
    private String title;//标题
    private String message;//消息
    private boolean isBtnShow;//是否显示btn
    private String btnText;//按钮文字

    public boolean isLogoShow() {
        return isLogoShow;
    }

    public void setLogoShow(boolean logoShow) {
        isLogoShow = logoShow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isBtnShow() {
        return isBtnShow;
    }

    public void setBtnShow(boolean btnShow) {
        isBtnShow = btnShow;
    }

    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
