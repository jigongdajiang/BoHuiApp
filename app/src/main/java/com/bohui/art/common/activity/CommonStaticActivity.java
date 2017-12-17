package com.bohui.art.common.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.bohui.art.R;
import com.bohui.art.common.widget.title.DefaultTitleBar;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class CommonStaticActivity extends BaseWebActivity {
    public static final String WEB_TITLE = "web_title";
    private String mTitle;
    private DefaultTitleBar defaultTitleBar;
    @BindView(R.id.rel_no_net)
    RelativeLayout rel_no_net;
    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        mTitle = getIntent().getStringExtra(WEB_TITLE);
    }

    @Override
    public int getLayoutId() {
        return super.getLayoutId();
    }

    @Override
    protected void initTitle() {
        defaultTitleBar = new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle(mTitle)
                .builder();
    }

    @Override
    protected WebViewClient createWebViewClient() {
        return new HXWebViewClient();
    }
    private class HXWebViewClient extends NormalWebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            rel_no_net.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            rel_no_net.setVisibility(View.VISIBLE);
            defaultTitleBar.getViewHolder().setTextViewText(R.id.tv_title_title, mTitle == null ? "" : mTitle);
        }
    }
}
