package net.vrallev.android.base.work;

import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public class TaskHandler {

    private final ExecutorService mExecutorService;
    private final Handler mHandler;

    public TaskHandler() {
        this(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    public TaskHandler(ExecutorService executorService) {
        mExecutorService = executorService;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public <Params, Result> PendingResult<Result> executeAsync(final PendingResultCallback<Result> callback, final Task<Params, Result> task, final Params params) {
        final PendingResult<Result> pendingResult = new PendingResult<>(callback);

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                pendingResult.setResult(task.execute(params));
            }
        });

        return pendingResult;
    }

    public <Result> PendingResult<Result> executeAsync(final PendingResultCallback<Result> callback, final TaskNoParams<Result> task) {
        final PendingResult<Result> pendingResult = new PendingResult<>(callback);

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                pendingResult.setResult(task.execute());
            }
        });

        return pendingResult;
    }

    public class PendingResult<T> {

        private WeakReference<PendingResultCallback<T>> mPendingResponseCallbackWeakReference;
        private T mResult;

        private boolean mResponseSet;
        private boolean mHasResult;

        private PendingResult(PendingResultCallback<T> pendingResultCallback) {
            setPendingResultCallback(pendingResultCallback);
        }

        public synchronized void setPendingResultCallback(PendingResultCallback<T> pendingResultCallback) {
            mPendingResponseCallbackWeakReference = new WeakReference<>(pendingResultCallback);

            if (mHasResult && !mResponseSet) {
                setResult(mResult);
            }
        }

        private synchronized void setResult(T result) {
            mResult = result;
            mHasResult = true;

            final PendingResultCallback<T> pendingResultCallback = mPendingResponseCallbackWeakReference.get();

            if (pendingResultCallback != null && !mResponseSet) {
                mResponseSet = true;

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        pendingResultCallback.onResponseArrived(mResult);
                    }
                });
            }
        }

        public synchronized T getResult() {
            return mResult;
        }
    }

    public interface PendingResultCallback<T> {
        public void onResponseArrived(T response);
    }
}
