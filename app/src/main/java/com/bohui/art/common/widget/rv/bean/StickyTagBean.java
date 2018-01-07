package com.bohui.art.common.widget.rv.bean;

/**
 * @author gaojigong
 * @version V1.0
 * @Description: 现在通用的组标签实体
 * @date 16/11/14.
 */
public class StickyTagBean{
    private long headerId;
    private String stickyDes;

    public long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(long headerId) {
        this.headerId = headerId;
    }

    public String getStickyDes() {
        return stickyDes;
    }

    public void setStickyDes(String stickyDes) {
        this.stickyDes = stickyDes;
    }
}