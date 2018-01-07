package com.bohui.art.common.fragment;

import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 * 通过FragmentManager管理的Fragment
 * 因为没有和ViewPager结合 所以其懒加载方案通过onResume和hide控制
 */


public abstract class AbsMianFragment<P extends BasePresenter,M extends BaseModel> extends AbsNetBaseFragment<P,M> {
    @Override
    public void onResume() {
        super.onResume();
        if (!this.isHidden()) {
            come();
        } else {
            leave();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            come();
        } else {
            leave();
        }
    }

    /**
     * 切换回来
     */
    protected abstract void come();

    /**
     * 切走
     */
    protected abstract void leave();
}
