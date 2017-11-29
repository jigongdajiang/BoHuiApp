package com.jimeijf.core.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.jimeijf.core.toast.ToastShow;

/**
 * @author : gaojigong
 * @date : 2017/11/16
 * @description:
 * BaseFragment 和 BaseActivity通用的功能辅助工具类
 * 业务层可以集成该对象，进行其它公共功能的扩展
 */


public class BaseHelperUtil {
    private Object baseContext;//这个可能是Activity 也可能是 Fragment，最终通过强制类型转换来实现

    public BaseHelperUtil(Object baseContext) {
        this.baseContext = baseContext;
    }

    /**
     * 所有用到baseContext的地方都要在验证合格的基础上才行
     * @return
     */
    private boolean verifyBaseContext(){
        return isBaseActivity() || isBaseFragment();
    }
    private boolean isBaseActivity(){
        return baseContext != null && baseContext instanceof Activity;
    }
    private boolean isBaseFragment(){
        return baseContext != null && baseContext instanceof Fragment;
    }
    protected BaseActivity change2Activity(){
        if(isBaseActivity()){
            return (BaseActivity) baseContext;
        }
        return null;
    }
    protected BaseFragment change2Fragment(){
        if(isBaseFragment()){
            return (BaseFragment) baseContext;
        }
        return null;
    }


    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startAty(Class<?> cls, Bundle bundle) {
        if(verifyBaseContext()){
            Intent intent = new Intent();
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            if(isBaseActivity()){
                intent.setClass(change2Activity(),cls);
                change2Activity().startActivity(intent);
            }else{
                intent.setClass(change2Fragment().getActivity(),cls);
                change2Fragment().startActivity(intent);
            }
        }
    }
    /**
     * 通过Class跳转界面
     **/
    public void startAty(Class<?> cls) {
        startAty(cls,null);
    }
    /**
     * 通过Class跳转界面
     **/
    public void startAtyForResult(Class<?> cls, int requestCode) {
        startAtyForResult(cls, null, requestCode);
    }
    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startAtyForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        if(verifyBaseContext()){
            Intent intent = new Intent();
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            if(isBaseActivity()){
                intent.setClass(change2Activity(),cls);
                change2Activity().startActivityForResult(intent,requestCode);
            }else{
                intent.setClass(change2Fragment().getActivity(),cls);
                change2Fragment().startActivityForResult(intent,requestCode);
            }
        }
    }

    /**
     * 吐司功能
     */
    public void toast(String content,int duration){
        if(verifyBaseContext()){
            if(isBaseActivity()){
                ToastShow.show(change2Activity(),content,duration);
            }else{
                ToastShow.show(change2Fragment().getActivity(),content,duration);
            }
        }
    }
    public void toastLong(String content){
        toast(content, Toast.LENGTH_LONG);
    }
    public void toastShort(String content){
        toast(content, Toast.LENGTH_SHORT);
    }


}
