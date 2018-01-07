package com.bohui.art.mine.attention.mvp;

import com.bohui.art.bean.mine.MyAttentionResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class MyAttentionPresenter extends MyAttentionContact.Presenter {
    @Override
    public void myAttention() {
        mRxManage.add(mModel.myAttention().subscribeWith(new AppProgressSubScriber<MyAttentionResult>(mView,MyAttentionContact.TAG_MY_ATTENTION,mView) {
            @Override
            protected void onResultSuccess(MyAttentionResult result) {
                mView.myAttentionSuccess(result);
            }
        }));
    }
}
