package com.bohui.art.classify.mvp;

import com.bohui.art.bean.classify.ClassifyLevel2Result;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 * 根据一级类型得到二级类型
 * 首页一级列表页通用
 */


public interface ClassifyContact {
    String URL_GET_CLASSIFY_LEVEL2 = "";
    String TAG_GET_CLASSIFY_LEVEL2 = "tag_get_classify_level2";
    interface  Model extends BaseModel{
        Observable<ClassifyLevel2Result> getClassifyLevel2(String level1);
    }
    interface View extends BaseLoadingView{
        void getClassifyLevel2Success(ClassifyLevel2Result result);
    }
    abstract class Presenter extends BasePresenter<Model,View> {
        public abstract void getClassifyLevel2(String level1);
    }
}
