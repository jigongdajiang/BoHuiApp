package com.bohui.art.home.mvp;

import com.bohui.art.bean.home.ClassifyLevelResult;
import com.bohui.art.bean.home.RecommendResult;
import com.bohui.art.bean.home.TypeResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public interface HomeContact {
    String URL_GET_CLASSIFY_LEVET1 = "home/oneClass";
    String TAG_GET_CLASSIFY_LEVET1 = "url_get_classify_levet1";

    String URL_GET_RECOMMEND = "home/recomedList";
    String TAG_GET_RECOMMEND = "tag_get_recommend";

    String URL_GET_TYPE = "home/category";
    String TAG_GET_TYPE = "TAG_GET_TYPE";

    interface IHomeModel extends BaseModel{
        Observable<ClassifyLevelResult> getClassifyLevel1();
    }
    interface IHomeView extends BaseLoadingView{
        void getClassifyLevel1Success(ClassifyLevelResult result);
    }
    abstract class AbsHomePresenter extends BasePresenter<IHomeModel,IHomeView>{
        public abstract void getClassifyLevel1();
    }


    interface IRecommendModel extends BaseModel{
        Observable<RecommendResult> getRecommend();
    }
    interface IRecommendView extends BaseLoadingView{
        void getRecommendSuccess(RecommendResult result);
    }
    abstract class AbsRecommendPresenter extends BasePresenter<IRecommendModel,IRecommendView>{
        public abstract void getRecommend();
    }

    interface ITypedModel extends BaseModel{
        Observable<TypeResult> getTypeInfo(long classType);
    }
    interface ITypedView extends BaseLoadingView{
        void getTypeInfoSuccess(TypeResult result);
    }
    abstract class AbsTypedPresenter extends BasePresenter<ITypedModel,ITypedView>{
        public abstract void getTypeInfo(long classType);
    }
}
