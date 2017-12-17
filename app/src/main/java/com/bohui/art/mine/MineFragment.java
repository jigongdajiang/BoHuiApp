package com.bohui.art.mine;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.found.GuideItemAdapter;
import com.bohui.art.found.GuideItemBean;
import com.bohui.art.home.RecommendFragment;
import com.bohui.art.mine.accountedit.AccountEditActivity;
import com.bohui.art.mine.attention.MyAttentionActivity;
import com.bohui.art.mine.collect.MyCollectActivity;
import com.bohui.art.mine.order.MyOrderActivity;
import com.bohui.art.mine.setting.SettingActivity;
import com.framework.core.base.BaseHelperUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/9
 * @description:
 */


public class MineFragment extends AbsNetBaseFragment {
    @BindView(R.id.rv_mine)
    RecyclerView rv_mine;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        //Top
        UserBean userBean = new UserBean();
        userBean.setAvrUrl(RecommendFragment.imgs[0]);
        userBean.setName("济公大奖");
        userBean.setDes("坚持到底就是胜利");
        final MineTopAdapter mineTopAdapter = new MineTopAdapter(mContext,userBean);
        delegateAdapter.addAdapter(mineTopAdapter);

        GuideItemAdapter foundItemAdapter = new GuideItemAdapter(mContext);
        List<GuideItemBean> guideItemBeans = new ArrayList<>();

        GuideItemBean collectItem = new GuideItemBean("我的收藏",R.mipmap.ic_collect,0,R.dimen.dp_10);
        guideItemBeans.add(collectItem);

        GuideItemBean attentionItem = new GuideItemBean("我的关注",R.mipmap.ic_attention,1,R.dimen.dp_1);
        guideItemBeans.add(attentionItem);

        GuideItemBean customizationItem = new GuideItemBean("我的定制",R.mipmap.ic_mine_customization,2,R.dimen.dp_1);
        guideItemBeans.add(customizationItem);

        GuideItemBean settingItem = new GuideItemBean("设置",R.mipmap.ic_setting,3,R.dimen.dp_1);
        guideItemBeans.add(settingItem);
        for(GuideItemBean itemBean : guideItemBeans){
            itemBean.setRightArrowResId(R.mipmap.ic_right_more);
        }

        foundItemAdapter.setDatas(guideItemBeans);

        delegateAdapter.addAdapter(foundItemAdapter);

        rv_mine.setLayoutManager(layoutManager);
        rv_mine.setAdapter(delegateAdapter);

        rv_mine.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                if(adapter instanceof MineTopAdapter){
                    ((BaseHelperUtil)mHelperUtil).startAty(AccountEditActivity.class);
                }else if(adapter instanceof GuideItemAdapter){
                    GuideItemBean itemBean = (GuideItemBean) adapter.getData(position);
                    switch (itemBean.getTypeId()){
                        case 0:
                            ((BaseHelperUtil)mHelperUtil).startAty(MyCollectActivity.class);
                            break;
                        case 1:
                            ((BaseHelperUtil)mHelperUtil).startAty(MyAttentionActivity.class);
                            break;
                        case 2:
                            ((BaseHelperUtil)mHelperUtil).startAty(MyOrderActivity.class);
                            break;
                        case 3:
                            ((BaseHelperUtil)mHelperUtil).startAty(SettingActivity.class);
                            break;
                    }
                }
            }
        });
    }
}
