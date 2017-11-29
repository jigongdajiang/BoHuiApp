package com.framework.core.util;

import android.os.StrictMode;
import android.os.SystemClock;

/**
 * @author : gaojigong
 * @date : 2017/10/12
 * @description:
 */


public class StrictUtil {
    public static void openStrictMode(boolean open) {
        if (open) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls() //自定义的耗时调用 API等级11，使用StrictMode.noteSlowCode
                    .detectDiskReads()//磁盘读取操作
                    .detectDiskWrites()//磁盘写入操作
                    .detectNetwork()   //网络操作 or .detectAll() for all detectable problems
                    .penaltyLog() //违规时 在Logcat 中打印违规异常信息(建议)
//                    .penaltyDialog() //违规时 弹出违规提示对话框
//                    .penaltyFlashScreen() //违规时 屏幕闪 API等级11
//                    .penaltyDeath() //违规时crash
//                    .penaltyDeathOnNetwork() //网络违规时crash
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects() //API等级11
                    .penaltyLog()//违规时 在Logcat 中打印违规异常信息(建议)
//                    .penaltyDeath() //违规时crash
                    .build());
        }
    }

    private static long SLOW_CALL_THRESHOLD = 500;
    /**
     * 自定义耗时操作严苛规则，如果任务执行实现大于minTime，则认为严苛模式违规
     */
    public static void executeTask(Runnable task,long minTime) {
        long startTime = SystemClock.uptimeMillis();
        task.run();
        long cost = SystemClock.uptimeMillis() - startTime;
        if (cost > minTime) {
            StrictMode.noteSlowCall("slowCall cost=" + cost);
        }
    }
    public static void executeTask(Runnable task) {
        executeTask(task,SLOW_CALL_THRESHOLD);
    }
}
