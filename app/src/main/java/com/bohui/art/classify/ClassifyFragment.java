package com.bohui.art.classify;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.framework.core.fragment.BaseFragmentAdapter;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;
import com.widget.smallelement.viewpager.RollCtrlViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/9
 * @description:
 */


public class ClassifyFragment extends AbsNetBaseFragment {
    @BindView(R.id.rv_classify)
    RecyclerView rv;
    @BindView(R.id.vp_classify)
    RollCtrlViewPager rollCtrlViewPager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    public void initView() {
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        ClassifyTypeAdapter classifyTypeAdapter = new ClassifyTypeAdapter(mContext);
        List<ClassifyTypeBean> classifyTypeBeans = new ArrayList<>();
        ClassifyTypeBean beanRm = new ClassifyTypeBean();
        beanRm.setType("热门分类");
        beanRm.setTypeId(0);
        beanRm.setChecked(true);
        classifyTypeBeans.add(beanRm);
        for(int i=1;i<16;i++){
            ClassifyTypeBean bean = new ClassifyTypeBean();
            bean.setType("分类"+i);
            bean.setTypeId(i);
            bean.setChecked(false);
            classifyTypeBeans.add(bean);
        }
        classifyTypeAdapter.setDatas(classifyTypeBeans);
        delegateAdapter.addAdapter(classifyTypeAdapter);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(delegateAdapter);

        List<Fragment> fragments = new ArrayList<>();
        for(ClassifyTypeBean bean :classifyTypeBeans){
            fragments.add(ClassifyTypeFragment.newInstance(bean));
        }
        BaseFragmentAdapter fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(),fragments);
        rollCtrlViewPager.setAdapter(fragmentAdapter);
        rollCtrlViewPager.setCurrentItem(0);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                rollCtrlViewPager.setCurrentItem(position);
                List<ClassifyTypeBean> datas = adapter.getDatas();
                for(int i=0;i<datas.size();i++){
                    if(position == i){
                        datas.get(i).setChecked(true);
                    }else{
                        datas.get(i).setChecked(false);
                    }
                }
                delegateAdapter.notifyDataSetChanged();
            }
        });
    }
}
