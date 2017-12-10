package com.framework.core.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.framework.core.rxcore.RxManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : gaojigong
 * @date : 2017/11/13
 * @description:
 */


public abstract class BaseFragment <P extends BasePresenter, M extends BaseModel> extends Fragment {
    /**
     * 存储跟View
     * 这样不会因Fragment被移除而被清空，能保留原View的状态
     * 这当Fragment被不断创建时，View不会重新创建，所以在对View进行设置时，需要进行刷新操作
     * 例如 addHeader等，否则会导致重复添加
     */
    protected View rootView;
    /**
     * Presenter
     */
    protected P mPresenter;
    /**
     * Model
     */
    protected M mModel;
    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 总线管理器
     */
    protected RxManager mRxManager;
    /**
     * 该层Activity和Fragment共有的基础功能
     * 例如本层的基础功能工具具有启动Activity，显示吐司功能
     * 如果需要该工具类能自由操作操控该基类内的对象，对该基类的需要对象提供get方法即可
     * 本基类的集成子类如果有这种情况会累加类似对象的持有，目的是减少BaseXXX的代码冗余
     */
    protected AbsHelperUtil mHelperUtil;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mHelperUtil = createHelperUtil();
        mRxManager=new RxManager();
        doBeforeOnCreateView();
    }
    /**
     * 钩子方法
     * 创建当前层的通用帮助类，主要是将fragment和activity的相同代码统一管理
     * @return
     */
    protected AbsHelperUtil createHelperUtil() {
        return new BaseHelperUtil(this);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(getLayoutId(), container, false);
        }else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if(parent != null){
                parent.removeView(rootView);
            }
        }
        mUnbinder = ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
        mModel = createModel();
        if(mPresenter!=null){
            mPresenter.mContext=mContext;
        }
        initView();
        initModel();
        initPresenter();
        extraInit();
    }
    /**
     * 钩子方法
     * 设置layout前配置
     * 例如
     *  设置主题
     *  横竖屏操作
     *  状态栏操作等
     */
    protected abstract void doBeforeOnCreateView();

    /**
     * 钩子方法
     * 指定布局资源Id
     */
    public abstract int getLayoutId();
    /**
     * 钩子方法
     * 规范MVP架构  创建P
     */
    protected abstract P createPresenter();

    /**
     * 钩子方法
     * 规范MVP架构  创建M
     */
    protected abstract M createModel();

    /**
     * 钩子方法
     * 初始化View
     */
    public abstract void initView();

    /**
     * 钩子方法
     * 初始化Model
     */
    protected abstract void initModel();

    /**
     * 钩子方法
     * 初始化Presenter
     * 这里一般会先进行MVP关联，其中V部分根据业务需要会实现集成BaseView的接口
     *  例如 mPresenter.setMV(mModel,this);
     */
    public abstract void initPresenter();

    /**
     * 钩子方法
     * 扩展初始化
     * 提供其它初始化设置的地方，除了必须的初始化之外的操作重写此方法实现
     */
    protected void extraInit(){

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHelperUtil = null;
        if (mPresenter != null)
            mPresenter.onDestroy();
        if(mRxManager!=null) {
            mRxManager.clear();
        }
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
    }
}
