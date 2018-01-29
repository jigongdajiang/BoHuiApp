package com.bohui.art.mine.attention.mvp;

import com.bohui.art.bean.mine.MyAttentionResult;
import com.bohui.art.common.net.AppProgressSubScriber;
import com.bohui.art.mine.collect.mvp.MyCollectParam;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class MyAttentionPresenter extends MyAttentionContact.Presenter {
    @Override
    public void myAttention(MyCollectParam param) {
        mRxManage.add(mModel.myAttention(param).subscribeWith(new AppProgressSubScriber<MyAttentionResult>(mView,MyAttentionContact.TAG_MY_ATTENTION,mView) {
            @Override
            protected void onResultSuccess(MyAttentionResult result) {
                mView.myAttentionSuccess(result);
            }
        }));
    }
}
