package com.bohui.art.mine.collect.mvp;

import com.bohui.art.bean.common.PageParam;

/**
 * @author : gaojigong
 * @date : 2018/1/27
 * @description:
 */


public class MyCollectParam extends PageParam {
    private long uid;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
