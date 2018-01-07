package com.bohui.art.common.util.helperutil;

import android.app.Activity;
import android.support.v4.app.FragmentManager;

import com.bohui.art.R;
import com.bohui.art.common.widget.dialog.DialogFactory;
import com.framework.core.base.BaseHelperUtil;
import com.framework.core.util.DisplayUtil;
import com.framework.core.util.ResUtil;
import com.widget.smallelement.dialog.BasePowfullDialog;


/**
 * @author : gaojigong
 * @date : 2017/11/30
 * @description:
 */


public class AbsBaseHelperUtil extends BaseHelperUtil {
    public enum DialogType {
        LOADING, SAMPLE_MESSAGE;

        private String title;
        private String message;

        DialogType() {
        }

        DialogType(String title, String message) {
            this.title = title;
            this.message = message;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    private BasePowfullDialog mDialog;
    private DialogType mCurrentType = DialogType.LOADING;

    public AbsBaseHelperUtil(Object baseContext) {
        super(baseContext);
    }

    private void createDialog() {
        switch (mCurrentType) {
            case LOADING:
                createLoadingDialog();
                break;
            case SAMPLE_MESSAGE:
                createMessageDialog();
                break;
        }
    }

    private void createMessageDialog() {
        Activity activity = null;
        FragmentManager fragmentManager = null;
        if (isBaseActivity()) {
            activity = change2Activity();
            fragmentManager = change2Activity().getSupportFragmentManager();

        } else if (isBaseFragment()) {
            activity = change2Fragment().getActivity();
            fragmentManager = change2Fragment().getChildFragmentManager();
        }
        if (activity != null && fragmentManager != null) {
            mDialog = DialogFactory.
                    createDefalutMessageDialog(
                            activity,
                            fragmentManager,
                            mCurrentType.getTitle(),
                            mCurrentType.getMessage(),
                            "确定");
        }
    }

    private void createLoadingDialog() {
        Activity activity = null;
        FragmentManager fragmentManager = null;
        if (isBaseActivity()) {
            activity = change2Activity();
            fragmentManager = change2Activity().getSupportFragmentManager();

        } else if (isBaseFragment()) {
            activity = change2Fragment().getActivity();
            fragmentManager = change2Fragment().getChildFragmentManager();
        }
        if (activity != null && fragmentManager != null) {
            int screenWidth = DisplayUtil.getScreenWidth(activity);
            float dHeight = ResUtil.getResDimen(activity, R.dimen.dialog_loading_wh);
            double scacle = dHeight / screenWidth;
            mDialog = new BasePowfullDialog
                    .Builder(R.layout.dialog_loading, activity, fragmentManager)
                    .setDialogWidthForScreen(scacle)
                    .setDialogTag(BasePowfullDialog.LOADING_TAG)
                    .builder();
        }
    }

    public BasePowfullDialog getDialogFragment(DialogType type) {
        mCurrentType = type;
        createDialog();
        return mDialog;
    }

    public BasePowfullDialog getLoadingDialog(){
        return getDialogFragment(DialogType.LOADING);
    }
    public void showLoadingDialog() {
        getDialogFragment(DialogType.LOADING).showDialog();
    }

    public void missLoadingDialog() {
        if(mDialog != null){
            mDialog.miss();
        }
    }

    public void showMsgDialg(String title, String message) {
        DialogType type = DialogType.SAMPLE_MESSAGE;
        type.setTitle(title);
        type.setMessage(message);
        getDialogFragment(type).showDialog();
    }

    public void showMsgDialg(String message) {
        showMsgDialg("提示", message);
    }
}
