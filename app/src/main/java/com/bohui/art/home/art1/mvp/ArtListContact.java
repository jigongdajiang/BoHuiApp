package com.bohui.art.home.art1.mvp;

import com.bohui.art.bean.home.ArtListResult;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;
import com.framework.core.base.BaseView;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 * 艺术品三级列表页
 *
 * 二级分类型列表页
 * 三级列列表页通用
 */


public interface ArtListContact {
    String URL_GET_ART_LIST = "";
    String TAG_GET_ART_LIST = "tag_get_art_list";
    interface Model extends BaseModel{
        Observable<ArtListResult> getArtList(String level2,int pageSize,int pageNumber);
    }
    interface View extends BaseView{
        void getArtListSuccess(ArtListResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void getArtList(String level2,int pageSize,int pageNumber);
    }
}
