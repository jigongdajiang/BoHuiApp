package com.bohui.art.mine.upload;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.bohui.art.R;
import com.bohui.art.common.app.AppParams;
import com.framework.core.toast.ToastShow;
import com.framework.core.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author hujiajun
 * @version V1.0
 * @Description: 拍照工具类
 * @date 16/08/01.
 */
public class PopupWindowPhotoHelper {
    // 定义拍照后存放图片的文件位置和名称，使用完毕后可以方便删除
    private File tempFile;
    //裁剪图片请求码
    public static final int PHOTO_RESOULT = 0x03;
    // 使用照相机拍照获取图片
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    // 使用相册中的图片
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    private Object mObject;
    private Activity currentContext;

    private PopupWindow mPop;

    public static PopupWindowPhotoHelper getInstance(Object object) {
        return new PopupWindowPhotoHelper(object);
    }

    /**
     * 构造器
     */
    private PopupWindowPhotoHelper(Object object) {
        this.mObject = object;
        initPop();
    }

    public void destory() {
        mPop.dismiss();
    }

    private void initPop() {
        if (mObject instanceof Fragment) {
            currentContext = ((Fragment) mObject).getActivity();
        } else if (mObject instanceof Activity) {
            currentContext = (Activity) mObject;
        }
        mPop = new PopupWindow(currentContext);
        View view = View.inflate(currentContext, R.layout.dialog_takephoto, null);
        mPop.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        mPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPop.setBackgroundDrawable(new BitmapDrawable());
        mPop.setFocusable(true);
        mPop.setOutsideTouchable(true);
        mPop.setContentView(view);
        mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                changWindowAlpha(1.0f);
            }
        });
        mPop.setAnimationStyle(R.style.BottomToTopAnim);//设置popup近处是的动画
        mPop.update();
        TextView takpe_tv = view.findViewById(R.id.takpe_tv);
        TextView select_tv = view.findViewById(R.id.select_tv);
        TextView btn_cancel = view.findViewById(R.id.btn_cancel);
        takpe_tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPop.dismiss();
                if (onTakePhotoListener != null) {
                    onTakePhotoListener.onTakePhoto();
                }
            }
        });
        select_tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pickPhoto();
                mPop.dismiss();

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPop.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPop.dismiss();
            }
        });
    }

    /**
     * popupwindow 消失的时候清楚Flags 否则会有黑色页面闪
     */
    private void changWindowAlpha(float alpha) {
        WindowManager.LayoutParams lp = currentContext.getWindow().getAttributes();
        if (alpha == 1.0f) {
            currentContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            currentContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        lp.alpha = alpha;
        currentContext.getWindow().setAttributes(lp);
    }

    /**
     * 显示Pop
     */
    public void showPhotoPop(View parent) {
        if (mPop != null) {
            String fileName = "bdtemp.jpg";
            tempFile = new File(FileUtil.getCacheDir(currentContext), fileName);
            if (tempFile.exists()) {
                tempFile.delete();
            }
            //改变背景颜色  背景有一层灰色
            changWindowAlpha(0.6f);
            mPop.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * 当点击拍照按钮的回调，当点击时应该先申请权限,申请成功后方可走拍照流程
     */
    public interface OnTakePhotoListener {
        void onTakePhoto();
    }

    private OnTakePhotoListener onTakePhotoListener;

    public void setOnTakePhotoListener(OnTakePhotoListener onTakePhotoListener) {
        this.onTakePhotoListener = onTakePhotoListener;
    }

    /**
     * 拍照上传，调用此方法时确定是有权限的
     */
    public void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
                Uri uriForFile = FileProvider.getUriForFile(currentContext, "com.jimeijf.financing.fileprovider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            }
            if (mObject instanceof Fragment) {
                ((Fragment) mObject).startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
            } else if (mObject instanceof Activity) {
                ((Activity) mObject).startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
            }
        } else {
            ToastShow.showLong(currentContext, "内存卡不存在");
        }
    }

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if (mObject instanceof Fragment) {
            ((Fragment) mObject).startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
        } else if (mObject instanceof Activity) {
            ((Activity) mObject).startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
        }
        AppParams.getInstance().hasStartCarma = true;
    }

    /**
     * 选择图片后，获取图片的路径
     */
    public void doPhoto(int requestCode, Intent data) {
        if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {// 从相册取图片
            if (null != data) {
                Uri picUri = data.getData();
                if (picUri != null) {
                    cropImage(picUri);
                }
            }
        }
        if (requestCode == SELECT_PIC_BY_TACK_PHOTO) {// 拍照
            if (tempFile.exists()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri inputUri = FileProvider.getUriForFile(currentContext, "com.jimeijf.financing.fileprovider", tempFile);//通过FileProvider创建一个content类型的Uri
                    cropImage(inputUri);//设置输入类型
                } else {
                    cropImage(Uri.fromFile(tempFile));
                }
            }
        }
        if (requestCode == PHOTO_RESOULT) {
            if (tempFile.exists()) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(currentContext.getContentResolver().openInputStream(Uri.fromFile(tempFile)));
                    try {
                        assert bitmap != null;
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(tempFile));
                        bitmap.recycle();
                        uploadHeadImg(tempFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 上传头像
     *
     * @param headImg 头像图片文件
     */
    private void uploadHeadImg(File headImg) {
        File[] files = new File[1];
        files[0] = headImg;
        if (upLoadImgListener != null) {
            upLoadImgListener.upLoad(files);
        }
    }

    public interface UpLoadImgListener {
        void upLoad(File[] files);
    }

    private UpLoadImgListener upLoadImgListener;

    public void setUpLoadImgListener(UpLoadImgListener upLoadImgListener) {
        this.upLoadImgListener = upLoadImgListener;
    }

    /**
     * 修改图片大小
     */
    private void cropImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri outPutUri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {//获取图片的真实路径，再构建Content Uri
            Uri outPutUri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        }
        if (mObject instanceof Fragment) {
            ((Fragment) mObject).startActivityForResult(intent, PHOTO_RESOULT);
        } else if (mObject instanceof Activity) {
            ((Activity) mObject).startActivityForResult(intent, PHOTO_RESOULT);
        }
    }
}