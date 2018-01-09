package com.bohui.art.search.mvp;

import com.bohui.art.bean.search.SearchResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class SearchDetailPresenter extends SearchContact.AbsISearchDetailPresenter {
    @Override
    public void searchByTag() {
        mRxManage.add(mModel.searchByTag().subscribeWith(new AppProgressSubScriber<SearchResult>(mView,SearchContact.TAG_SEARCH_BY_TAG,mView) {
            @Override
            protected void onResultSuccess(SearchResult result) {
                mView.searchByTagSuccess(result);
            }
        }));
    }

    @Override
    public void filtrate() {
        mRxManage.add(mModel.filtrate().subscribeWith(new AppProgressSubScriber<SearchResult>(mView,SearchContact.TAG_FILTRATE,mView) {
            @Override
            protected void onResultSuccess(SearchResult result) {
                mView.filtrateSuccess(result);
            }
        }));
    }
}
