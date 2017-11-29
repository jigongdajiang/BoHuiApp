package com.bohui.art.common.widget.dialog;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.framework.core.util.ResUtil;
import com.bohui.art.R;
import com.widget.smallelement.dialog.BasePowfullDialog;


/**
 * @author gaojigong
 * @version V1.0
 * @Description: 提供创建几种默认Dialog的方法 如果有其它的可用自由构建模式创建
 * @date 2017/7/12.
 */

public class DialogFactory {
    /**
     * 项目中统一好的普通提示框(R.layout.dialog_message:标题，内容，左右按钮文字，左右按钮点击事件)
     * 样式在布局中已经指定好
     * 这里只设置内容显示情况
     * 这里只内容显示的变更，从效果采用默认的 如点击空区域不消失，点击返回键不消失。如果还有其它特殊定制，请直接用Builder模式配置
     */
    public static BasePowfullDialog createDefalutMessageDialog(Context context,
                                                               FragmentManager fragmentManager,
                                                               String title,
                                                               String content,
                                                               String leftBtnText,
                                                               String rightBtnText,
                                                               View.OnClickListener leftBtnClickListener,
                                                               View.OnClickListener rightBtnClickListener, int animRes) {
        if(context == null){
            return null;
        }
        BasePowfullDialog dialog;
            dialog = new BasePowfullDialog.Builder(R.layout.dialog_message, context, fragmentManager)
                    .builder();
            setDialogAttr(context, title, content, leftBtnText, rightBtnText, leftBtnClickListener, rightBtnClickListener, animRes, dialog);
            dialog.showDialog();
        return dialog;
    }

    private static void setDialogAttr(Context context, String title, String content, String leftBtnText, String rightBtnText, View.OnClickListener leftBtnClickListener, View.OnClickListener rightBtnClickListener, int animRes, BasePowfullDialog dialog) {
        dialog.setTextView(R.id.tv_dialog_title, title)
                .setTextView(R.id.tv_dialog_message, content)
                .setTextView(R.id.btn_dialog_left, leftBtnText)
                .setTextView(R.id.btn_dialog_right, rightBtnText)
                .setDialogAnim(animRes)
                .setViewOnClickListener(R.id.btn_dialog_left, leftBtnClickListener)
                .setViewOnClickListener(R.id.btn_dialog_right, rightBtnClickListener);

        if(TextUtils.isEmpty(leftBtnText) || TextUtils.isEmpty(rightBtnText)){
            dialog.getInsideView(R.id.alert_dialog_line).setVisibility(View.GONE);
        }
        if(TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)){
            //内容增加15dp的margin
            View contentView = dialog.getInsideView(R.id.tv_dialog_message);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentView.getLayoutParams();
            params.topMargin = ResUtil.getResDimensionPixelOffset(context,R.dimen.dp_15);
            contentView.setLayoutParams(params);
        }
    }

    /**
     * 只有内容的提示框 按钮为确定 点击确定消失
     */
    public static BasePowfullDialog createDefalutMessageDialog(Context context,
                                                               FragmentManager fragmentManager,
                                                               String content) {
        BasePowfullDialog dialog = createDefalutMessageDialog(context, fragmentManager, null, content, null, "确定", null, null, 0);
        dialog.setViewClickCancel(R.id.btn_dialog_right);
        return dialog;
    }

    /**
     * 只有标题 内容 的提示框 按钮为确定 点击确定消失
     */
    public static BasePowfullDialog createDefalutMessageDialog(Context context,
                                                               FragmentManager fragmentManager,
                                                               String title,
                                                               String content) {
        BasePowfullDialog dialog = createDefalutMessageDialog(context, fragmentManager, title, content, null, "确定", null, null, 0);
        dialog.setViewClickCancel(R.id.btn_dialog_right);
        return dialog;
    }

    /**
     * 只有标题 内容 的提示框 按钮为指定文字 点击消失
     */
    public static BasePowfullDialog createDefalutMessageDialog(Context context,
                                                               FragmentManager fragmentManager,
                                                               String title,
                                                               String content,
                                                               String rightBtnText) {
        BasePowfullDialog dialog = createDefalutMessageDialog(context, fragmentManager, title, content, null, rightBtnText, null, null, 0);
        dialog.setViewClickCancel(R.id.btn_dialog_right);
        return dialog;
    }

    /**
     * 只有标题 内容 的提示框 按钮为指定文字 点击有指定回调
     */
    public static BasePowfullDialog createDefalutMessageDialog(Context context,
                                                               FragmentManager fragmentManager,
                                                               String title,
                                                               String content,
                                                               String rightBtnText,
                                                               View.OnClickListener rightBtnClickListener) {
        return createDefalutMessageDialog(context, fragmentManager, title, content, null, rightBtnText, null, rightBtnClickListener, 0);
    }

    /**
     * 只有标题 内容 的提示框 左按钮文字为取消  右按钮文字为确定  确定有指定回调
     */
    public static BasePowfullDialog createDefalutMessageDialog(Context context,
                                                               FragmentManager fragmentManager,
                                                               String title,
                                                               String content,
                                                               View.OnClickListener rightBtnClickListener) {
        BasePowfullDialog dialog = createDefalutMessageDialog(context, fragmentManager, title, content, "取消", "确定", null, rightBtnClickListener, 0);
        dialog.setViewClickCancel(R.id.btn_dialog_left);
        return dialog;
    }

}
