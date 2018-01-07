package com.framework.core.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


import com.framework.core.rxcore.RxManager;
import com.framework.core.util.TUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : gaojigong
 * @date : 2017/11/13
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
     *
     * 这种类使用时一般要进行强制类型转换，并且使用时要进行类型判断和非空判断
     * 特别是异步操作时，因为为了不内存泄露，在OnDestroy中进行了置空。异步使用可能会导致空指针
     */
    protected AbsHelperUtil mHelperUtil;
    private Unbinder mUnbinder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelperUtil = createHelperUtil();
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
    protected  P createPresenter(){
        return TUtil.getT(this,0);
    }

    /**
     * 钩子方法
     * 规范MVP架构  创建M
     */
    protected  M createModel(){
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


    /**
     * 全局监控点击空白区域隐藏键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
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
