package com.bohui.art.found;

import com.bohui.art.R;

/**
 * @author : gaojigong
 * @date : 2017/12/13
 * @description:
 */


public class GuideItemBean {
    private String des;
    private int leftImgResId;
    private int typeId;
    private int marginTopResId = R.dimen.dp_1;
    private int rightArrowResId = 0;

    public GuideItemBean(String des, int leftImgResId, int typeId, int marginTopResId) {
        this.des = des;
        this.leftImgResId = leftImgResId;
        this.typeId = typeId;
        this.marginTopResId = marginTopResId;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getMarginTopResId() {
        return marginTopResId;
    }

    public void setMarginTopResId(int marginTopResId) {
        this.marginTopResId = marginTopResId;
    }

    public int getLeftImgResId() {
        return leftImgResId;
    }

    public void setLeftImgResId(int leftImgResId) {
        this.leftImgResId = leftImgResId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getRightArrowResId() {
        return rightArrowResId;
    }

    public void setRightArrowResId(int rightArrowResId) {
        this.rightArrowResId = rightArrowResId;
    }
}
