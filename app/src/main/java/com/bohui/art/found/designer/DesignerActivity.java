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
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.home.adapter.DesignerAdapter;
import com.bohui.art.home.bean.DesignerBean;
import com.bohui.art.search.SearchActivity;
import com.framework.core.base.BaseHelperUtil;
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


public class DesignerActivity extends AbsNetBaseActivity{
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;

    private RecyclerView rv;

    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter areaAdapter;
    private ListDropDownAdapter styleAdapter;
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
        rv = convertView.findViewById(R.id.rv);
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);

        final String headers[] = {"擅长领域", "擅长风格"};
        final String areas[] = {"不限", "KTV", "写字楼", "饭店"};
        final String styles[] = {"不限", "中国风", "欧美风"};

        final ListView areaView = new ListView(this);
        areaView.setDividerHeight(0);
        areaAdapter = new ListDropDownAdapter(this, Arrays.asList(areas));
        areaView.setAdapter(areaAdapter);

        final ListView styleView = new ListView(this);
        styleView.setDividerHeight(0);
        styleAdapter = new ListDropDownAdapter(this, Arrays.asList(styles));
        styleView.setAdapter(styleAdapter);

        areaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                areaAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : areas[position]);
                mDropDownMenu.closeMenu();
            }
        });

        styleView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                styleAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : styles[position]);
                mDropDownMenu.closeMenu();
            }
        });

        popupViews.add(areaView);
        popupViews.add(styleView);

        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, convertView);

        DesignerAdapter designerAdapter = new DesignerAdapter(mContext);
        List<DesignerBean> designerBeans = new ArrayList<>();
        for(int i=0;i<20;i++){
            DesignerBean designerBean = new DesignerBean();
            designerBeans.add(designerBean);
        }
        designerAdapter.setDatas(designerBeans);
        delegateAdapter.addAdapter(designerAdapter);
    }
}
