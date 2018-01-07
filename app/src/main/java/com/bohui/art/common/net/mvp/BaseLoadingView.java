package com.bohui.art.common.net.mvp;

import com.bohui.art.common.net.IBasePwProgressDialog;
import com.framework.core.base.BaseView;

/**
 * @author : gaojigong
 * @date : 2018/1/6
 * @description:
 * 具有异常处理和Loading框创建的组合接口
 * 这样虽然耦合高，但是不需要在每个initPresenter中取单独设置有没有Loading加载机制
 * 在控制层可以不用改Activity或者Fragment 值修改BasePresenter即可
 */


public interface BaseLoadingView extends BaseView,IBasePwProgressDialog{
}
