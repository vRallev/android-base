package net.vrallev.android.base.util;

import android.os.AsyncTask;
import android.os.Build;

import java.util.concurrent.Executor;

/**
 * Source: https://gist.github.com/2773896
 * 
 * Uses level 11 APIs when possible to use parallel/serial executors and falls back to standard execution if API level
 * is below 11.
 * 
 * @author Markus
 */
public class AsyncTaskExecutionHelper {
    static class HoneycombExecutionHelper {
        public static <P> void execute(AsyncTask<P, ?, ?> asyncTask, boolean parallel, P... params) {
            Executor executor = parallel ? AsyncTask.THREAD_POOL_EXECUTOR : AsyncTask.SERIAL_EXECUTOR;
            asyncTask.executeOnExecutor(executor, params);
        }
    }

    public static <P> void executeParallel(AsyncTask<P, ?, ?> asyncTask, P... params) {
        execute(asyncTask, true, params);
    }

    public static <P> void executeSerial(AsyncTask<P, ?, ?> asyncTask, P... params) {
        execute(asyncTask, false, params);
    }

    private static <P> void execute(AsyncTask<P, ?, ?> asyncTask, boolean parallel, P... params) {
        if (Build.VERSION.SDK_INT >= 11) {
            HoneycombExecutionHelper.execute(asyncTask, parallel, params);
        } else {
            asyncTask.execute(params);
        }
    }
}
