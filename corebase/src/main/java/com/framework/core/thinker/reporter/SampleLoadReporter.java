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

package com.framework.core.thinker.reporter;

import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;

import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.util.UpgradePatchRetry;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.io.File;


/**
 * 定义了Tinker在加载补丁时的一些回调
 * 1.回调运行在加载的进程，它有可能是各个不一样的进程。
 *   我们可以通过tinker.isMainProcess或者tinker.isPatchProcess知道当前是否是主进程，patch补丁合成进程。
 * 2.回调发生的时机是我们调用installTinker之后，某些进程可能并不需要installTinker。
 */
public class SampleLoadReporter extends DefaultLoadReporter {
    private final static String TAG = "SampleLoadReporter";

    public SampleLoadReporter(Context context) {
        super(context);
    }

    /**
     * 所有的补丁合成请求都需要先通过PatchListener的检查过滤。
     * 框架认为，检查不通过，没必要去启动 TinkerPatchService
     * 这次检查不通过的回调，它运行在发起请求的进程。默认我们只是打印日志
     * @param patchFile
     * @param errorCode
     */
    @Override
    public void onLoadPatchListenerReceiveFail(final File patchFile, int errorCode) {
        super.onLoadPatchListenerReceiveFail(patchFile, errorCode);
        SampleTinkerReport.onTryApplyFail(errorCode);
    }

    /**
     *
     * 这个是无论加载失败或者成功都会回调的接口，
     * 它返回了本次加载所用的时间、返回码等信息。
     * 默认我们只是简单的输出这个信息，你可以在这里加上监控上报逻辑。
     *
     * @param patchDirectory  补丁文件的跟路径
     * @param loadCode 返回码 {@code ShareConstants.ERROR_LOAD_OK} 意味着成功
     * @param cost 加载所用的时间，毫秒
     */
    @Override
    public void onLoadResult(File patchDirectory, int loadCode, long cost) {
        super.onLoadResult(patchDirectory, loadCode, cost);
        switch (loadCode) {
            case ShareConstants.ERROR_LOAD_OK:
                SampleTinkerReport.onLoaded(cost);
                break;
        }
        Looper.getMainLooper().myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                if (UpgradePatchRetry.getInstance(context).onPatchRetryLoad()) {
                    SampleTinkerReport.onReportRetryPatch();
                }
                return false;
            }
        });
    }

    /**
     * 在加载过程捕捉到异常，十分希望你可以把错误信息反馈给我们。
     * 默认我们会直接卸载补丁包
     * @param e
     * @param errorCode
     *  ERROR_LOAD_EXCEPTION_UNKNOWN	-1	没有捕获到的java crash
     *  ERROR_LOAD_EXCEPTION_DEX	-2	在加载dex过程中捕获到的crash
     *  ERROR_LOAD_EXCEPTION_RESOURCE	-3	在加载res过程中捕获到的crash
     *  ERROR_LOAD_EXCEPTION_UNCAUGHT	-4	没有捕获到的非java crash,这个是补丁机制的安全模式
     */
    @Override
    public void onLoadException(Throwable e, int errorCode) {
        super.onLoadException(e, errorCode);
        SampleTinkerReport.onLoadException(e, errorCode);
    }

    /**
     * 部分文件的md5与meta中定义的不一致。
     * 默认我们为了安全考虑，依然会清空补丁。
     * @param file
     * @param fileType
     *  TYPE_PATCH_FILE	1	补丁文件
     *  TYPE_PATCH_INFO	2	"patch.info"补丁版本配置文件
     *  TYPE_DEX	3	在Dalvik合成全量的Dex文件
     *  TYPE_DEX_OPT	4	odex文件
     *  TYPE_LIBRARY	5	library文件
     8  TYPE_RESOURCE	6	资源文件
     */
    @Override
    public void onLoadFileMd5Mismatch(File file, int fileType) {
        super.onLoadFileMd5Mismatch(file, fileType);
        SampleTinkerReport.onLoadFileMisMatch(fileType);
    }

    /**
     * 在加载过程中，发现部分文件丢失的回调。
     * 默认若是dex，dex优化文件或者lib文件丢失，
     * 我们将尝试从补丁包去修复这些丢失的文件。
     * 若补丁包或者版本文件丢失，将卸载补丁包。
     * @param file
     * @param fileType
     * @param isDirectory
     */
    @Override
    public void onLoadFileNotFound(File file, int fileType, boolean isDirectory) {
        super.onLoadFileNotFound(file, fileType, isDirectory);
        SampleTinkerReport.onLoadFileNotFound(fileType);
    }

    /**
     * 加载过程补丁包的检查失败，
     * 这里可以通过错误码区分，
     * 例如签名校验失败、tinkerId不一致等原因。
     * 默认我们将会卸载补丁包
     * @param patchFile
     * @param errorCode
     *  ERROR_PACKAGE_CHECK_SIGNATURE_FAIL	-1	签名校验失败
     *  ERROR_PACKAGE_CHECK_PACKAGE_META_NOT_FOUND	-2	找不到"assets/package_meta.txt"文件
     *  ERROR_PACKAGE_CHECK_DEX_META_CORRUPTED	-3	"assets/dex_meta.txt"信息损坏
     *  ERROR_PACKAGE_CHECK_LIB_META_CORRUPTED	-4	"assets/so_meta.txt"信息损坏
     *  ERROR_PACKAGE_CHECK_APK_TINKER_ID_NOT_FOUND	-5	找不到基准apk AndroidManifest中的TINKER_ID
     *  ERROR_PACKAGE_CHECK_PATCH_TINKER_ID_NOT_FOUND	-6	找不到补丁中"assets/package_meta.txt"中的TINKER_ID
     *  ERROR_PACKAGE_CHECK_TINKER_ID_NOT_EQUAL	-7	基准版本与补丁定义的TINKER_ID不相等
     *  ERROR_PACKAGE_CHECK_RESOURCE_META_CORRUPTED	-8	"assets/res_meta.txt"信息损坏
     *  ERROR_PACKAGE_CHECK_TINKERFLAG_NOT_SUPPORT	-9	tinkerFlag不支持补丁中的某些类型的更改，例如补丁中存在资源更新，但是使用者指定不支持资源类型更新。
     */
    @Override
    public void onLoadPackageCheckFail(File patchFile, int errorCode) {
        super.onLoadPackageCheckFail(patchFile, errorCode);
        SampleTinkerReport.onLoadPackageCheckFail(errorCode);
    }

    /**
     * patch.info是用来管理补丁包版本的文件，
     * 这是info文件损坏的回调。
     * 默认我们会卸载补丁包，因为此时我们已经无法恢复了。
     * @param oldVersion
     * @param newVersion
     * @param patchInfoFile
     */
    @Override
    public void onLoadPatchInfoCorrupted(String oldVersion, String newVersion, File patchInfoFile) {
        super.onLoadPatchInfoCorrupted(oldVersion, newVersion, patchInfoFile);
        SampleTinkerReport.onLoadInfoCorrupted();
    }

    /**
     * 系统OTA后，为了加快补丁的执行，我们会采用解释模式来执行补丁。
     * @param type
     * @param e
     */
    @Override
    public void onLoadInterpret(int type, Throwable e) {
        super.onLoadInterpret(type, e);
        SampleTinkerReport.onLoadInterpretReport(type, e);
    }

    /**
     * 补丁包版本升级的回调，只会在主进程调用。
     * 默认我们会杀掉其他所有的进程(保证所有进程代码的一致性)，并且删掉旧版本的补丁文件。
     * @param oldVersion
     * @param newVersion
     * @param patchDirectoryFile
     * @param currentPatchName
     */
    @Override
    public void onLoadPatchVersionChanged(String oldVersion, String newVersion, File patchDirectoryFile, String currentPatchName) {
        super.onLoadPatchVersionChanged(oldVersion, newVersion, patchDirectoryFile, currentPatchName);
    }

}
