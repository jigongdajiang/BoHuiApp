package com.bohui.art.common.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.bohui.art.R;
import com.bohui.art.common.helperutil.AbsBaseHelperUtil;
import com.framework.core.log.PrintLog;

import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/11/30
 * @description:
 */


public class BaseWebActivity extends AbsBaseActivity {
    public static final String WEB_URL_CONTENT = "url_content";

    @BindView(R.id.webView)
    protected WebView webView;//必有的WebView

    protected String url_content;//传入的地址
    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {
        getIntentData();//获取外来数据
        initTitle();//有标题是在此方法设置标题
        webSet();
        showPage();
    }
    /**
     * 钩子，用于获取从其它页面进入时的传值，子类重写是要有surper
     * 默认是有url的获取的
     */
    protected void getIntentData() {
        url_content = this.getIntent().getStringExtra(WEB_URL_CONTENT);
    }

    /**
     * 钩子方法，用来设置标题
     */
    protected void initTitle() {

    }

    /**
     * 设置WebView参数，有默认实现
     */
    @SuppressLint("SetJavaScriptEnabled")
    protected void webSet() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setUseWideViewPort(true);//默认网页缩小至webview大小
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDatabaseEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setWebViewClient(createWebViewClient());
    }

    /**
     * 指定Client,只是这个不同时可重写覆盖次方法
     */
    protected WebViewClient createWebViewClient() {
        return new NormalWebViewClient();
    }

    /**
     * 展示内容，默认是会显示加载框的
     */
    protected void showPage() {
        if (url_content != null && url_content.length() > 0) {
            if(mHelperUtil != null && mHelperUtil instanceof AbsBaseHelperUtil){
                ((AbsBaseHelperUtil)mHelperUtil).showLoadingDialog();
            }
            webView.loadUrl(url_content);
        }
    }

    protected void hideLoadingDialog() {
        if(mHelperUtil != null && mHelperUtil instanceof AbsBaseHelperUtil){
            ((AbsBaseHelperUtil)mHelperUtil).missLoadingDialog();
        }
    }
    /**
     * 默认实现
     */
    public class NormalWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideLoadingDialog();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        //支持https
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }



    /**
     * 同步Cookie
     */
    public static boolean syncCookie(Context context, String url, List<String> cookies) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(context);
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();// 移除
        cookieManager.removeAllCookie();//移除之前的Cookie
        String oldCookie = cookieManager.getCookie(url);
        if(oldCookie != null){
            PrintLog.d("oldCookie", oldCookie);
        }
        for(String cookie : cookies){
            cookieManager.setCookie(url, cookie);//设置新的
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().sync();
        }
        String newCookie = cookieManager.getCookie(url);
        PrintLog.d("newCookie", newCookie);
        return !TextUtils.isEmpty(newCookie);
    }
}
