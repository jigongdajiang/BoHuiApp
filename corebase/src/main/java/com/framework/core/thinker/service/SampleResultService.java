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

package com.framework.core.thinker.service;


import com.framework.core.rxcore.RxManager;
import com.framework.core.thinker.util.Utils;
import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.TinkerServiceInternals;

import java.io.File;

/**
 * patch补丁合成进程将合成结果返回给主进程的类
 * 默认我们在DefaultTinkerResultService会杀掉:patch进程，
 * 假设当前是补丁升级并且成功了，我们会杀掉当前进程，让补丁包更快的生效。
 * 若是修复类型的补丁包并且失败了，我们会卸载补丁包。
 */
public class SampleResultService extends DefaultTinkerResultService {
    private static final String TAG = "Tinker.SampleResultService";

    public static final String PATCH_RESULT_EVENT = "patch_down_result";
    public static final String PATCH_RESULT_EVENT_SUBJECT = "patch_down_result_subject";


    /**
     * 补丁合成的最终返回结果
     * @param result
     *      result.isSuccess	补丁合成操作是否成功。
     *      result.rawPatchFilePath	原始的补丁包路径。
     *      result.costTime	本次补丁合成的耗时。
     *      result.e	本次补丁合成是否出现异常，null为没有异常。
     *      result.patchVersion	补丁文件的md5, 有可能为空@Nullable。
     */
    @Override
    public void onPatchResult(final PatchResult result) {
        if (result == null) {
            TinkerLog.e(TAG, "SampleResultService received null result!!!!");
            return;
        }
        TinkerLog.i(TAG, "SampleResultService receive result: %s", result.toString());

        //first, we want to kill the recover process
        TinkerServiceInternals.killTinkerPatchServiceProcess(getApplicationContext());

        RxManager rxManager = new RxManager();
        rxManager.post(PATCH_RESULT_EVENT,result.isSuccess,PATCH_RESULT_EVENT_SUBJECT);
        // is success and newPatch, it is nice to delete the raw file, and restart at once
        // for old patch, you can't delete the patch file
        if (result.isSuccess) {
            //删除原来的补丁
            deleteRawPatchFile(new File(result.rawPatchFilePath));

            //not like TinkerResultService, I want to restart just when I am at background!
            //if you have not install tinker this moment, you can use TinkerApplicationHelper api
            if (checkIfNeedKill(result)) {
                if (Utils.isBackground()) {
                    TinkerLog.i(TAG, "it is in background, just restart process");
                    restartProcess();
                } else {
                    //we can wait process at background, such as onAppBackground
                    //or we can restart when the screen off
                    TinkerLog.i(TAG, "tinker wait screen to restart process");
                    new Utils.ScreenState(getApplicationContext(), new Utils.ScreenState.IOnScreenOff() {
                        @Override
                        public void onScreenOff() {
                            restartProcess();
                        }
                    });
                }
            } else {
                TinkerLog.i(TAG, "I have already install the newly patch version!");
            }
        }
    }

    /**
     * you can restart your process through service or broadcast
     */
    private void restartProcess() {
        TinkerLog.i(TAG, "app is background now, i can kill quietly");
        //you can send service or broadcast intent to restart your process
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
