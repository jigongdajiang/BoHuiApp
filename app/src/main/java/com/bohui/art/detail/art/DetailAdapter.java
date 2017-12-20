package com.bohui.art.detail.art;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.rv.ItemType;
import com.bohui.art.detail.art.bean.ArtDetailBean;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/18
 * @description:
 */


public class DetailAdapter extends BaseAdapter<ArtDetailBean> {
    public DetailAdapter(Context context, ArtDetailBean artDetailBean) {
        super(context);
        addItem(artDetailBean);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.ART_DETAIL_DETAIL;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.detail_art_detail;
    }

    private WebSettings webSettings;

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtDetailBean itemData, int position) {
        WebView wv_detail = holder.getView(R.id.webView);
        wv_detail.setFocusable(false);
        wv_detail.loadUrl(itemData.getDetailUrl());
        webSettings = wv_detail.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv_detail.setWebViewClient(new GoodsDetailWebViewClient());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
    private class GoodsDetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
}
