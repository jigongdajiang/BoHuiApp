package com.framework.core.thinker.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.framework.core.log.PrintLog;
import com.framework.core.util.FileUtil;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author : gaojigong
 * @date : 2017/11/1
 * @description:
 */


public class DownLoadPatchService extends IntentService {
    private static final String ACTION_DOWNLOAD_PATCH = "DownLoadPatchService.action.DOWNLOD_PATCH";
    public static final String EXTRA_PATCH_PATH = "DownLoadPatchService.extra.PATCH_URL";

    public static void startDownLoadPatchService(Context context, String url) {
        Intent intent = new Intent(context, DownLoadPatchService.class);
        intent.setAction(ACTION_DOWNLOAD_PATCH);
        intent.putExtra(EXTRA_PATCH_PATH, url);
        context.startService(intent);
    }

    public DownLoadPatchService() {
        super(DownLoadPatchService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action)) {
            if (ACTION_DOWNLOAD_PATCH.equals(action)) {
                String url = intent.getStringExtra(EXTRA_PATCH_PATH);
                if (!TextUtils.isEmpty(url)) {
                    String localPatchPath = downLoadImg(this, url);
                    try {
                        if (!TextUtils.isEmpty(localPatchPath)) {
                            PrintLog.e("补丁下载完成,开始修复，补丁地址-->" + localPatchPath);
                            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), localPatchPath);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String downLoadImg(Context context, String imgUrl) {
        String localFilePath;
        HttpURLConnection con = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            File filePic;
            //创建本地文件
            String savePath = FileUtil.getCacheDir(context);
            String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1, imgUrl.length());
            localFilePath = savePath + fileName;
            filePic = new File(localFilePath);
            if (filePic.exists()) {
                filePic.delete();
            } else {
                if (!filePic.getParentFile().exists()) {
                    filePic.getParentFile().mkdirs();
                }
            }
            // 构造URL
            URL url = new URL(imgUrl);
            // 打开连接
            con = (HttpURLConnection) url.openConnection();
            //获得文件的长度
            int contentLength = con.getContentLength();
            System.out.println("长度 :" + contentLength);
            // 输入流
            is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            os = new FileOutputStream(localFilePath);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
                os.flush();
            }
            return localFilePath;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 完毕，关闭所有链接
            if (null != os) {
                try {
                    os.close();
                    os = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                    is = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != con) {
                con.disconnect();
                con = null;
            }
        }
    }
}
