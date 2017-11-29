package com.framework.core.toast;

import android.content.Context;
import android.widget.Toast;

import com.framework.core.R;

/**
 * @author : gaojigong
 * @date : 2017/11/14
 * @description:
 */


public class SysToastStrategy implements IToastStrategy {
    private Toast mToast;
    @Override
    public void show(Context context, String str, int duration) {
        if(mToast == null){
            mToast = Toast.makeText(context,str,duration);
        }else{
            mToast.setText(str);
        }
        mToast.show();
    }

    @Override
    public void cancel() {
        if(mToast != null){
            mToast.cancel();
            mToast = null;
        }
    }
}
