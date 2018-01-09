package com.bohui.art.search.mvp;

import com.bohui.art.bean.search.SearchResult;
import com.bohui.art.bean.search.SearchTagResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class SearchPresenter extends SearchContact.AbsISearchPresenter {
    @Override
    public void getSearchTag() {
        mRxManage.add(mModel.getSearchTag().subscribeWith(new AppProgressSubScriber<SearchTagResult>(mView,SearchContact.TAG_GET_SEARCH_TAG,mView) {
            @Override
            protected void onResultSuccess(SearchTagResult result) {
                mView.getSearchTagSuccess(result);
            }
        }));
    }
}
