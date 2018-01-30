package com.bohui.art.search;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.found.DesignerAttrResult;
import com.bohui.art.bean.found.DesignerItemBean;
import com.bohui.art.bean.found.DesignerListParam;
import com.bohui.art.bean.found.DesignerListResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.found.designer.DesignerListContact;
import com.bohui.art.found.designer.DesignerListModel;
import com.bohui.art.found.designer.DesignerListPresenter;
import com.bohui.art.home.adapter.DesignerAdapter;


import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/17
 * @description:
 * 设计师搜索结果页
 */


public class SearchResultDesignerActivity extends AbsNetBaseActivity<DesignerListPresenter,DesignerListModel> implements DesignerListContact.View {
    @BindView(R.id.rv)
    RecyclerView rv;

    public static final String SEARCH_KEY = "search_key";
    private String mSearchKey;

    private DesignerAdapter adapter;
    private DesignerListParam param;

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        mSearchKey = getIntent().getStringExtra(SEARCH_KEY);
        param = new DesignerListParam();
        param.setName(mSearchKey);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result_designer;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setLeftText(mSearchKey)
                .builder();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        adapter = new DesignerAdapter(mContext);
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
        mPresenter.getDesignerList(param);
    }

    @Override
    public void getDesignerAttrSuccess(DesignerAttrResult result) {

    }

    @Override
    public void getDesignerListSuccess(DesignerListResult result) {
        adapter.replaceAllItem(new ArrayList<DesignerItemBean>());
    }
}
