package com.bohui.art.mine.attentioncompany.mvp;

import com.bohui.art.bean.mine.MyAttentionCompanyResult;
import com.bohui.art.common.net.AppProgressSubScriber;
import com.bohui.art.mine.collect.mvp.MyCollectParam;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public class MyAttentionCompanyPresenter extends MyAttentionCompanyContact.Presenter {

    @Override
    public void getMyAttentionCompany(MyCollectParam param) {
        mRxManage.add(mModel.getMyAttentionCompany(param).subscribeWith(new AppProgressSubScriber<MyAttentionCompanyResult>(mView, MyAttentionCompanyContact.TAG_MY_ATTENTION_COMPANY, mView) {
            @Override
            protected void onResultSuccess(MyAttentionCompanyResult result) {
                mView.getMyAttentionCompanySuccess(result);
            }
        }));
    }
}
