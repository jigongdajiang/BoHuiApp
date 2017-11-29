/*
 * Tencent is pleased to support the open source community by making Tinker available.
 *
 * Copyright (C) 2016 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jimeijf.core.thinker.reporter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.jimeijf.core.thinker.crash.SampleUncaughtExceptionHandler;
import com.jimeijf.core.thinker.info.BuildInfo;
import com.jimeijf.core.thinker.util.Utils;
import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

import java.io.File;
import java.util.Properties;


/**
 * 是用来过滤Tinker收到的补丁包的修复、升级请求，
 * 也就是决定我们是不是真的要唤起:patch进程去尝试补丁合成。
 * 若检查成功，我们会调用TinkerPatchService.runPatchService唤起:patch进程，去尝试完成补丁合成操作。
 * 反之，会回调检验失败的接口。事实上，你只需要复写patchCheck函数即可。若检查失败，会在LoadReporter的onLoadPatchListenerReceiveFail中回调。
 */
public class SamplePatchListener extends DefaultPatchListener {
    private static final String TAG = "Tinker.SamplePatchListener";

    protected static final long NEW_PATCH_RESTRICTION_SPACE_SIZE_MIN = 60 * 1024 * 1024;

    private final int maxMemory;

    public SamplePatchListener(Context context) {
        super(context);
        maxMemory = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        TinkerLog.i(TAG, "application maxMemory:" + maxMemory);
    }

    /**
     * because we use the defaultCheckPatchReceived method
     * the error code define by myself should after {@code ShareConstants.ERROR_RECOVER_INSERVICE
     *
     * @param path
     * @param newPatch
     * @return
     *  返回码如下
     *  ERROR_PATCH_DISABLE	-1	当前tinkerFlag为不可用状态。
     *  ERROR_PATCH_NOTEXIST	-2	输入的临时补丁包文件不存在。
     *  ERROR_PATCH_RUNNING	-3	当前:patch补丁合成进程正在运行。
     *  ERROR_PATCH_INSERVICE	-4	不能在:patch补丁合成进程，发起补丁的合成请求。
     *  ERROR_PATCH_JIT	-5	补丁不支持 N 之前的 JIT 模式。
     *  其他		在SamplePatchListener里面，我们还检查了当前Rom剩余空间，最大内存，是否是GooglePlay渠道等条件。
     */
    @Override
    public int patchCheck(String path, String patchMd5) {
        File patchFile = new File(path);
        TinkerLog.i(TAG, "receive a patch file: %s, file size:%d", path, SharePatchFileUtil.getFileOrDirectorySize(patchFile));
        int returnCode = super.patchCheck(path, patchMd5);

        if (returnCode == ShareConstants.ERROR_PATCH_OK) {
            returnCode = Utils.checkForPatchRecover(NEW_PATCH_RESTRICTION_SPACE_SIZE_MIN, maxMemory);
        }

        if (returnCode == ShareConstants.ERROR_PATCH_OK) {
            SharedPreferences sp = context.getSharedPreferences(ShareConstants.TINKER_SHARE_PREFERENCE_CONFIG, Context.MODE_MULTI_PROCESS);
            //optional, only disable this patch file with md5
            int fastCrashCount = sp.getInt(patchMd5, 0);
            if (fastCrashCount >= SampleUncaughtExceptionHandler.MAX_CRASH_COUNT) {
                returnCode = Utils.ERROR_PATCH_CRASH_LIMIT;
            }
        }
        // Warning, it is just a sample case, you don't need to copy all of these
        // Interception some of the request
        if (returnCode == ShareConstants.ERROR_PATCH_OK) {
            Properties properties = ShareTinkerInternals.fastGetPatchPackageMeta(patchFile);
            if (properties == null) {
                returnCode = Utils.ERROR_PATCH_CONDITION_NOT_SATISFIED;
            } else {
                String platform = properties.getProperty(Utils.PLATFORM);
                TinkerLog.i(TAG, "get platform:" + platform);
                // check patch platform require
                if (platform == null || !platform.equals(BuildInfo.PLATFORM)) {
                    returnCode = Utils.ERROR_PATCH_CONDITION_NOT_SATISFIED;
                }
            }
        }

        SampleTinkerReport.onTryApply(returnCode == ShareConstants.ERROR_PATCH_OK);
        return returnCode;
    }
}
