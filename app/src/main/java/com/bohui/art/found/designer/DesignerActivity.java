package com.bohui.art.found.designer;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.found.DesignerAttrBean;
import com.bohui.art.bean.found.DesignerAttrResult;
import com.bohui.art.bean.found.DesignerItemBean;
import com.bohui.art.bean.found.DesignerListParam;
import com.bohui.art.bean.found.DesignerListResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.detail.designer.DesignerDetailActivity;
import com.bohui.art.home.adapter.DesignerAdapter;
import com.bohui.art.search.SearchActivity;
import com.framework.core.base.BaseHelperUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;
import com.widget.smallelement.dropdown.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 * 设计师列表页
 */


public class DesignerActivity extends AbsNetBaseActivity<DesignerListPresenter,DesignerListModel> implements DesignerListContact.View{
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;

    private RecyclerView rv;

    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter areaAdapter;
    private ListDropDownAdapter styleAdapter;
    private List<DesignerAttrBean> areaBeans;
    private List<DesignerAttrBean> styleBeans;
    private DesignerAdapter designerAdapter;
    private final String headers[] = {"擅长领域", "擅长风格"};
    private DesignerListParam param;

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        param = new DesignerListParam();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_designer;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("设计师")
                .setRightImage1(R.mipmap.ic_search)
                .setRightImage1ClickListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(SearchActivity.SEARCH_TYPE,2);
                        ((BaseHelperUtil)mHelperUtil).startAty(SearchActivity.class,bundle);
                    }
                })
                .builder();
        View convertView =  LayoutInflater.from(mContext).inflate(R.layout.layout_common_rv,null);

        final ListView areaView = new ListView(this);
        areaView.setDividerHeight(0);
        areaBeans = new ArrayList<>();
        DesignerAttrBean areaHeader = new DesignerAttrBean();
        areaHeader.setName("擅长领域");
        areaHeader.setTid(0);
        areaHeader.setType(1);
        areaBeans.add(areaHeader);
        areaAdapter = new ListDropDownAdapter(this,areaBeans);
        areaView.setAdapter(areaAdapter);

        final ListView styleView = new ListView(this);
        styleView.setDividerHeight(0);
        styleBeans = new ArrayList<>();
        DesignerAttrBean styleHeader = new DesignerAttrBean();
        styleHeader.setName("擅长风格");
        styleHeader.setTid(0);
        styleHeader.setType(2);
        areaBeans.add(styleHeader);
        styleAdapter = new ListDropDownAdapter(this, styleBeans);
        styleView.setAdapter(styleAdapter);

        areaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                areaAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(areaBeans.get(position).getName());
                mDropDownMenu.closeMenu();
                param.setGoodfiled(areaBeans.get(position).getTid());
                mPresenter.getDesignerList(param);
            }
        });

        styleView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                styleAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(styleBeans.get(position).getName());
                mDropDownMenu.closeMenu();
                param.setGoodstyle(styleBeans.get(position).getTid());
                mPresenter.getDesignerList(param);
            }
        });

        popupViews.add(areaView);
        popupViews.add(styleView);

        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, convertView);
        rv = convertView.findViewById(R.id.rv);
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        designerAdapter = new DesignerAdapter(mContext);
        designerAdapter.setDelegateAdapter(delegateAdapter);
        delegateAdapter.addAdapter(designerAdapter);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                DesignerItemBean itemBean = (DesignerItemBean) adapter.getData(position);
                DesignerDetailActivity.comeIn(DesignerActivity.this, AppFuntion.getUid(),itemBean.getDid());
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        mPresenter.getDesignerAttr();
    }

    @Override
    public void getDesignerAttrSuccess(DesignerAttrResult result) {
        areaBeans.clear();
        DesignerAttrBean areaHeader = new DesignerAttrBean();
        areaHeader.setName("擅长领域");
        areaHeader.setTid(0);
        areaHeader.setType(1);
        result.getAreaList().add(0,areaHeader);
        areaBeans.addAll(result.getAreaList());
        areaAdapter.notifyDataSetChanged();
        styleBeans.clear();
        DesignerAttrBean styleHeader = new DesignerAttrBean();
        styleHeader.setName("擅长风格");
        styleHeader.setTid(0);
        styleHeader.setType(2);
        result.getStyleList().add(0,styleHeader);
        styleBeans.addAll(result.getStyleList());
        param.setGoodfiled(0);
        param.setGoodstyle(0);
        mPresenter.getDesignerList(param);
    }

    @Override
    public void getDesignerListSuccess(DesignerListResult result) {
        designerAdapter.replaceAllItem(result.getDesignerList());
    }
}
