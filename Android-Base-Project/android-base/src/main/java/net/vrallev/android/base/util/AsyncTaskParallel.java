package net.vrallev.android.base.util;

import android.os.AsyncTask;

/**
 * @author Ralf Wondratschek
 */
public abstract class AsyncTaskParallel<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    @SafeVarargs
    public final AsyncTask<Params, Progress, Result> executeParallel(Params... params) {
        return executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
    }

    @SafeVarargs
    public final AsyncTask<Params, Progress, Result> executeSerial(Params... params) {
        return executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
    }
}
