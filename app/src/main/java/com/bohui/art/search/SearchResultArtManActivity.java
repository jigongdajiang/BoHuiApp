package com.bohui.art.search;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.common.ArtListResult;
import com.bohui.art.bean.common.ArtManListParam;
import com.bohui.art.bean.found.ArtManItemBean;
import com.bohui.art.bean.found.ArtManListResult;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.bohui.art.bean.home.RecommendDesignerBean;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.mvp.ArtListContact;
import com.bohui.art.common.mvp.ArtListModel;
import com.bohui.art.common.mvp.ArtListPresenter;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.found.artman.ArtManListAdapter;
import com.bohui.art.found.artman.mvp.ArtManListContact;
import com.bohui.art.found.artman.mvp.ArtManListModel;
import com.bohui.art.found.artman.mvp.ArtManListPresenter;
import com.bohui.art.home.adapter.DesignerAdapter;
import com.bohui.art.home.art1.Art2Adapter;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.smallelement.dialog.BasePowfullDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/17
 * @description:
 * 艺术家搜索结果页
 */


public class SearchResultArtManActivity extends AbsNetBaseActivity<ArtManListPresenter,ArtManListModel> implements ArtManListContact.IArtManListView {
    @BindView(R.id.rv)
    RecyclerView rv;

    public static final String SEARCH_KEY = "search_key";
    private String mSearchKey;

    private ArtManListAdapter adapter;
    private ArtManListParam param;

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        mSearchKey = getIntent().getStringExtra(SEARCH_KEY);
        param = new ArtManListParam();
        param.setName(mSearchKey);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result_art_man;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setLeftText(mSearchKey)
                .builder();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        adapter = new ArtManListAdapter(mContext);
        adapter.setDelegateAdapter(delegateAdapter);
        delegateAdapter.addAdapter(adapter);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        mPresenter.getArtManList(param);
    }

    @Override
    public void getArtManListSuccess(ArtManListResult result) {
        adapter.replaceAllItem(result.getArtistList());
    }
}
