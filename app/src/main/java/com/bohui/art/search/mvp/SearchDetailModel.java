package com.bohui.art.search.mvp;

import com.bohui.art.bean.search.SearchResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class SearchDetailModel implements SearchContact.ISearchDetailModel {
    @Override
    public Observable<SearchResult> searchByTag() {
        return EasyHttp.post(SearchContact.URL_SEARCH_BY_TAG)
                .execute(SearchResult.class);
    }

    @Override
    public Observable<SearchResult> filtrate() {
        return EasyHttp.post(SearchContact.URL_FILTRATE)
                .execute(SearchResult.class);
    }
}
