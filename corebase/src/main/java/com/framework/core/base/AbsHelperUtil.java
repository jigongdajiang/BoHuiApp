package com.framework.core.base;

/**
 * @author : gaojigong
 * @date : 2017/11/16
 * @description:
 * BaseFragment 和 BaseActivity通用的功能辅助工具类
 * 业务层可以集成该对象，进行双基类功能的统一处理
 * 当前层功能
 *  1.启动Activity的功能
 *  2.吐司功能
 */


public class AbsHelperUtil {
    protected Object mBaseContext;//这个可能是Activity 也可能是 Fragment，最终通过强制类型转换来实现

    public AbsHelperUtil(Object baseContext) {
        this.mBaseContext = baseContext;
        if(null == mBaseContext || !verifyBaseContext()){
            throw new IllegalArgumentException("该工具内容必须为 BaseActivity 或者 BaseFragment");
        }
    }

    /**
     * 所有用到baseContext的地方都要在验证合格的基础上才行
     * @return
     */
    protected boolean verifyBaseContext(){
        return isBaseActivity() || isBaseFragment();
    }
    protected boolean isBaseActivity(){
        return mBaseContext != null && mBaseContext instanceof BaseActivity;
    }
    protected boolean isBaseFragment(){
        return mBaseContext != null && mBaseContext instanceof BaseFragment;
    }
    protected BaseActivity change2Activity(){
        if(isBaseActivity()){
            return (BaseActivity) mBaseContext;
        }
        return null;
    }
    protected BaseFragment change2Fragment(){
        if(isBaseFragment()){
            return (BaseFragment) mBaseContext;
        }
        return null;
    }
}
