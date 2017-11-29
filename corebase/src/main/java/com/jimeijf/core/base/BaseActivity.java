package com.jimeijf.core.base;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jimeijf.core.rxcore.RxManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : gaojigong
 * @date : 2017/11/13
 * @FileName:
 * @description:
 * 基础Activity
 * 模板初始化
 * 生命周期打印
 */


public abstract class BaseActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity{
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
    protected BaseHelperUtil mHelperUtil;
    private Unbinder mUnbinder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelperUtil = new BaseHelperUtil(this);
        mRxManager=new RxManager();
        mContext = this;
        doBeforeSetContentView();
        if(getLayoutId() > 0){
            //布局文件尽量有效，减少setContentView引发错误
            setContentView(getLayoutId());
        }
        mUnbinder = ButterKnife.bind(this);
        mPresenter = createPresenter();
        mModel = createModel();
        if(mPresenter!=null){
            mPresenter.mContext=this;
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
    protected abstract void doBeforeSetContentView();

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
    protected void onDestroy() {
        super.onDestroy();
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
