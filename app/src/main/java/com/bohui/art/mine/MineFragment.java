package com.bohui.art.mine;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.mine.UserBean;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.fragment.AbsMianFragment;
import com.bohui.art.found.GuideItemAdapter;
import com.bohui.art.found.GuideItemBean;
import com.bohui.art.home.RecommendFragment;
import com.bohui.art.mine.accountedit.AccountEditActivity;
import com.bohui.art.mine.attention.MyAttentionActivity;
import com.bohui.art.mine.attentioncompany.MyAttentionCompanyActivity;
import com.bohui.art.mine.collect.MyCollectActivity;
import com.bohui.art.mine.mvp.MineContact;
import com.bohui.art.mine.mvp.MineModel;
import com.bohui.art.mine.mvp.MinePresenter;
import com.bohui.art.bean.mine.MineInfoResult;
import com.bohui.art.mine.order.MyOrderActivity;
import com.bohui.art.mine.setting.SettingActivity;
import com.framework.core.base.BaseHelperUtil;
import com.framework.core.log.PrintLog;
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


public class MineFragment extends AbsMianFragment<MinePresenter,MineModel> implements MineContact.View{
    @BindView(R.id.rv_mine)
    RecyclerView rv_mine;

    private MineTopAdapter mineTopAdapter;

    private MineInfoResult mineInfoResult;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        //Top
        mineTopAdapter = new MineTopAdapter(mContext,new MineInfoResult());
        mineTopAdapter.setDelegateAdapter(delegateAdapter);
        delegateAdapter.addAdapter(mineTopAdapter);

        GuideItemAdapter foundItemAdapter = new GuideItemAdapter(mContext);
        List<GuideItemBean> guideItemBeans = new ArrayList<>();

        GuideItemBean collectItem = new GuideItemBean("我收藏的艺术品",R.mipmap.ic_collect,0,R.dimen.dp_10);
        guideItemBeans.add(collectItem);

        GuideItemBean attentionItem = new GuideItemBean("我关注的艺术家",R.mipmap.ic_attention,1,R.dimen.dp_1);
        guideItemBeans.add(attentionItem);
        GuideItemBean attentionCompanyItem = new GuideItemBean("我关注的艺术机构",R.mipmap.ic_attention,2,R.dimen.dp_1);
        guideItemBeans.add(attentionCompanyItem);

        GuideItemBean customizationItem = new GuideItemBean("我的定制",R.mipmap.ic_mine_customization,3,R.dimen.dp_1);
        guideItemBeans.add(customizationItem);

        GuideItemBean settingItem = new GuideItemBean("设置",R.mipmap.ic_setting,4,R.dimen.dp_1);
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
                    if(mineInfoResult != null){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(AccountEditActivity.ACCOUNT_INFO,mineInfoResult);
                        startAty(AccountEditActivity.class,bundle);
                    }
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
                            ((BaseHelperUtil)mHelperUtil).startAty(MyAttentionCompanyActivity.class);
                            break;
                        case 3:
                            ((BaseHelperUtil)mHelperUtil).startAty(MyOrderActivity.class);
                            break;
                        case 4:
                            ((BaseHelperUtil)mHelperUtil).startAty(SettingActivity.class);
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void come() {
        mPresenter.getUserInfo(AppFuntion.getUid());
    }

    @Override
    protected void leave() {

    }

    @Override
    public void getUserInfoSuccess(MineInfoResult result) {
        mineInfoResult = result;
        mineTopAdapter.refresh(result);
    }
}
