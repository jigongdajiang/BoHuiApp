package com.bohui.art.search.mvp;

import com.bohui.art.bean.search.SearchResult;
import com.bohui.art.bean.search.SearchTagResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class SearchModel implements SearchContact.ISearchModel {
    @Override
    public Observable<SearchTagResult> getSearchTag() {
        return EasyHttp.post(SearchContact.URL_GET_SEARCH_TAG)
                .execute(SearchTagResult.class);
    }
}
