package com.bohui.art.search.mvp;

import com.bohui.art.bean.search.SearchResult;
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
    String URL_GET_SEARCH_TAG = "";
    String TAG_GET_SEARCH_TAG = "TAG_GET_SEARCH_TAG";
    String URL_SEARCH_BY_TAG = "";
    String TAG_SEARCH_BY_TAG = "tag_search_by_tag";
    String URL_FILTRATE = "";
    String TAG_FILTRATE = "tag_filtrate";
    interface ISearchModel extends BaseModel{
        Observable<SearchTagResult> getSearchTag();
    }
    interface ISearchView extends BaseLoadingView{
       void getSearchTagSuccess(SearchTagResult result);
    }
    abstract class AbsISearchPresenter extends BasePresenter<ISearchModel,ISearchView>{
        public abstract void getSearchTag();
    }

    interface ISearchDetailModel extends BaseModel{
        Observable<SearchResult> searchByTag();
        Observable<SearchResult> filtrate();

    }
    interface ISearchDetailView extends BaseLoadingView{
        void searchByTagSuccess(SearchResult result);
        void filtrateSuccess(SearchResult result);

    }
    abstract class AbsISearchDetailPresenter extends BasePresenter<ISearchDetailModel,ISearchDetailView>{
        public abstract void searchByTag();
        public abstract void filtrate();
    }
}
