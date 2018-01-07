package com.framework.core.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.framework.core.log.PrintLog;
import com.framework.core.rxcore.RxManager;
import com.framework.core.util.TUtil;

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

    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    protected boolean hasCreateView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        showLife("onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLife("onCreate");
        mContext = getContext();
        mHelperUtil = createHelperUtil();
        mRxManager=new RxManager();
        doBeforeOnCreateView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        showLife("setUserVisibleHint  "+isVisibleToUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        showLife("onCreateView");
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
        showLife("onViewCreated");
        mPresenter = createPresenter();
        mModel = createModel();
        if(mPresenter!=null){
            mPresenter.mContext=mContext;
        }
        initView();
        initModel();
        initPresenter();
        hasCreateView = true;
        extraInit();
    }

    @Override
    public void onStart() {
        super.onStart();
        showLife("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        showLife("onResume");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        showLife("onHiddenChanged  "+hidden);
    }

    @Override
    public void onPause() {
        super.onPause();
        showLife("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        showLife("onStop");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showLife("onDestroyView");
        mHelperUtil = null;
        if (mPresenter != null)
            mPresenter.onDestroy();
        if(mRxManager!=null) {
            mRxManager.clear();
        }
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
        hasCreateView = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showLife("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        showLife("onDetach");
    }

    /**
     * 钩子方法
     * 创建当前层的通用帮助类，主要是将fragment和activity的相同代码统一管理
     * @return
     */
    protected AbsHelperUtil createHelperUtil() {
        return new BaseHelperUtil(this);
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
    protected P createPresenter(){
        return TUtil.getT(this,0);
    }

    /**
     * 钩子方法
     * 规范MVP架构  创建M
     */
    protected M createModel(){
        return TUtil.getT(this,1);
    }

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
     * 例如懒加载的调用
     */
    protected void extraInit(){

    }
    private void showLife(String method){
        PrintLog.i("Fragment_Life",this.getClass().getSimpleName()+"---"+method);
    }

    public void startAty(Class atyClass,Bundle bundle,boolean isFinish){
        if(mHelperUtil != null && mHelperUtil instanceof BaseHelperUtil){
            ((BaseHelperUtil)mHelperUtil).startAty(atyClass,bundle,isFinish);
        }
    }
    public void startAty(Class atyClass,Bundle bundle){
        startAty(atyClass,bundle,false);
    }
    public void startAty(Class atyClass){
        startAty(atyClass,new Bundle(),false);
    }
    public void startAty(Class atyClass,boolean isFinish){
        startAty(atyClass,new Bundle(),isFinish);
    }
    public void startAtyForResult(Class<?> cls, Bundle bundle, int requestCode){
        if(mHelperUtil != null && mHelperUtil instanceof BaseHelperUtil){
            ((BaseHelperUtil)mHelperUtil).startAtyForResult(cls,bundle,requestCode);
        }
    }
    public void startAtyForResult(Class<?> cls, int requestCode){
        startAtyForResult(cls,new Bundle(),requestCode);
    }

    /**
     * 吐司功能
     */
    public void toast(String content,int duration){
        if(mHelperUtil != null && mHelperUtil instanceof BaseHelperUtil){
            ((BaseHelperUtil)mHelperUtil).toast(content,duration);
        }
    }
    public void toastLong(String content){
        toast(content, Toast.LENGTH_LONG);
    }
    public void toastShort(String content){
        toast(content, Toast.LENGTH_SHORT);
    }
}
