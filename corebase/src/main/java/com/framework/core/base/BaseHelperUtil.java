package com.framework.core.base;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.framework.core.toast.ToastShow;


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


public class BaseHelperUtil extends AbsHelperUtil{
    public BaseHelperUtil(Object baseContext) {
        super(baseContext);
    }
    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startAty(Class<?> cls, Bundle bundle,boolean isCurrentFinish) {
        if(verifyBaseContext()){
            Intent intent = new Intent();
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            if(isBaseActivity()){
                BaseActivity activity = change2Activity();
                intent.setClass(activity,cls);
                activity.startActivity(intent);
                if(isCurrentFinish){
                    activity.finish();
                }
            }else{
                BaseFragment fragment = change2Fragment();
                intent.setClass(fragment.getActivity(),cls);
                fragment.startActivity(intent);
                if(isCurrentFinish){
                    fragment.getActivity().finish();
                }
            }
        }
    }
    /**
     * 通过Class跳转界面,且不关闭当前
     **/
    public void startAty(Class<?> cls) {
        startAty(cls,null,false);
    }
    public void startAty(Class<?> cls,boolean isCurrentFinsh) {
        startAty(cls,null,isCurrentFinsh);
    }
    public void startAty(Class<?> cls,Bundle bundle) {
        startAty(cls,bundle,false);
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
