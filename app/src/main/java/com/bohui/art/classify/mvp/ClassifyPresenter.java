package com.bohui.art.classify.mvp;

import com.bohui.art.bean.classify.ClassifyLevel2Result;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ClassifyPresenter extends ClassifyContact.Presenter {
    @Override
    public void getClassifyLevel2(String level1) {
        mRxManage.add(mModel.getClassifyLevel2(level1).subscribeWith(new AppProgressSubScriber<ClassifyLevel2Result>(mView, ClassifyContact.TAG_GET_CLASSIFY_LEVEL2) {
            @Override
            protected void onResultSuccess(ClassifyLevel2Result classifyLevel2Result) {
                mView.getClassifyLevel2Success(classifyLevel2Result);
            }
        }));
    }
}
