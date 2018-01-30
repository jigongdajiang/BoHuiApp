package com.bohui.art.mine.setting.suggest.mvp;

import com.bohui.art.bean.mine.SuggestSubmitResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class SuggestPresenter extends SuggestContanct.Presenter {
    @Override
    public void suggestSubmit(long uid,String advice) {
        mRxManage.add(mModel.suggestSubmit(uid,advice).subscribeWith(new AppProgressSubScriber<SuggestSubmitResult>(mView,SuggestContanct.TAG_SUGGEST,mView) {
            @Override
            protected void onResultSuccess(SuggestSubmitResult result) {
                mView.suggestSubmitSuccess(result);
            }
        }));
    }
}
