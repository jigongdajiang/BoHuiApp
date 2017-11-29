package com.framework.core.toast;

import android.content.Context;

/**
 * @author : gaojigong
 * @date : 2017/11/14
 * @description:
 */


public class EToastStrategy implements IToastStrategy {
    private EToast toast;
    @Override
    public void show(Context context, String str, int duration) {
        if(toast == null){
            toast = EToast.makeText(context,str,duration);
        }else{
            toast.setText(str);
        }
        toast.show();
    }

    @Override
    public void cancel() {
        if(toast != null){
            toast.destory();
            toast = null;
        }
    }
}
