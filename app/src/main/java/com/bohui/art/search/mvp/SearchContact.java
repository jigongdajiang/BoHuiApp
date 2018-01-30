package com.bohui.art.search.mvp;

import com.bohui.art.bean.search.SearchTagResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public interface SearchContact {
    String URL_GET_SEARCH_TAG = "login/getAllTags";
    String TAG_GET_SEARCH_TAG = "TAG_GET_SEARCH_TAG";
    interface ISearchModel extends BaseModel{
        Observable<SearchTagResult> getSearchTag();
    }
    interface ISearchView extends BaseLoadingView{
       void getSearchTagSuccess(SearchTagResult result);
    }
    abstract class AbsISearchPresenter extends BasePresenter<ISearchModel,ISearchView>{
        public abstract void getSearchTag();
    }

}
