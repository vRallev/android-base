package net.vrallev.android.base.work;

import android.os.Handler;
import android.os.Looper;

import net.vrallev.android.base.util.Cat;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
        if (callback == null || task == null) {
            throw new IllegalArgumentException("no null values allowed callback: " + callback + ", task: " + task);
        }

        final PendingResult<Result> pendingResult = new PendingResult<>(callback, task.getClass());

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                //noinspection ConstantConditions
                if (task != null) {
                    pendingResult.setResult(task.execute(params));
                } else {
                    pendingResult.setResult(null);
                }
            }
        });

        return pendingResult;
    }

    public <Params, Result> PendingResult<Result> executeAsync(final Object callback, final Task<Params, Result> task, final Params params) {
        if (callback == null || task == null) {
            throw new IllegalArgumentException("no null values allowed callback: " + callback + ", task: " + task);
        }

        final PendingResult<Result> pendingResult = new PendingResult<>(callback, task.getClass());

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                //noinspection ConstantConditions
                if (task != null) {
                    pendingResult.setResult(task.execute(params));
                } else {
                    pendingResult.setResult(null);
                }
            }
        });

        return pendingResult;
    }

    public <Result> PendingResult<Result> executeAsync(final PendingResultCallback<Result> callback, final TaskNoParams<Result> task) {
        if (callback == null || task == null) {
            throw new IllegalArgumentException("no null values allowed callback: " + callback + ", task: " + task);
        }

        final PendingResult<Result> pendingResult = new PendingResult<>(callback, task.getClass());

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                //noinspection ConstantConditions
                if (task != null) {
                    pendingResult.setResult(task.execute());
                } else {
                    pendingResult.setResult(null);
                }
            }
        });

        return pendingResult;
    }

    public <Result> PendingResult<Result> executeAsync(final Object callback, final TaskNoParams<Result> task) {
        if (callback == null || task == null) {
            throw new IllegalArgumentException("no null values allowed callback: " + callback + ", task: " + task);
        }

        final PendingResult<Result> pendingResult = new PendingResult<>(callback, task.getClass());

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                //noinspection ConstantConditions
                if (task != null) {
                    pendingResult.setResult(task.execute());
                } else {
                    pendingResult.setResult(null);
                }
            }
        });

        return pendingResult;
    }

    public class PendingResult<T> {

        private final Class<?> mTaskClass;

        private WeakReference<Object> mPendingResponseCallbackWeakReference;
        private T mResult;

        private boolean mResponseSet;
        private boolean mHasResult;

        private PendingResult(PendingResultCallback<T> pendingResultCallback, Class<?> task) {
            mTaskClass = task;
            setPendingResultCallback(pendingResultCallback);
        }

        private PendingResult(Object callback, Class<?> task) {
            mTaskClass = task;
            setPendingResultCallback(callback);
        }

        public synchronized void setPendingResultCallback(PendingResultCallback<T> pendingResultCallback) {
            mPendingResponseCallbackWeakReference = new WeakReference<Object>(pendingResultCallback);

            if (mHasResult && !mResponseSet) {
                setResult(mResult);
            }
        }

        public synchronized void setPendingResultCallback(Object callback) {
            mPendingResponseCallbackWeakReference = new WeakReference<>(callback);

            if (mHasResult && !mResponseSet) {
                setResult(mResult);
            }
        }

        public synchronized void removePendingResultCallback() {
            mPendingResponseCallbackWeakReference = null;
        }

        private synchronized void setResult(T result) {
            mResult = result;
            mHasResult = true;

            if (mPendingResponseCallbackWeakReference == null) {
                return;
            }

            final Object reference = mPendingResponseCallbackWeakReference.get();

            if (reference != null && !mResponseSet) {
                mResponseSet = true;

                mHandler.post(new Runnable() {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void run() {
                        if (reference instanceof PendingResultCallback) {
                            ((PendingResultCallback) reference).onResponseArrived(mResult);

                        } else {
                            try {
                                Class<?> clazz = reference.getClass();

                                Method method;
                                if (mResult != null) {
                                    method = clazz.getMethod("onResult", mResult.getClass());
                                } else {
                                    Class<?> returnType = findReturnType(mTaskClass);
                                    method = clazz.getMethod("onResult", returnType);
                                }

                                if (method.isAnnotationPresent(TaskResult.class)) {
                                    method.invoke(reference, mResult);
                                }
                            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                                // ignore
                            }

                        }
                    }
                });
            }
        }

        protected Class<?> findReturnType(Class<?> taskClass) {
            for (Method method : taskClass.getMethods()) {
                if (!"execute".equals(method.getName())) {
                    continue;
                }

                for (Type genericInterface : taskClass.getGenericInterfaces()) {
                    if (!(genericInterface instanceof ParameterizedType)) {
                        continue;
                    }

                    ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                    String interfaceName = parameterizedType.getRawType().toString();

                    if (interfaceName != null && (interfaceName.endsWith(Task.class.getName()) || interfaceName.endsWith(TaskNoParams.class.getName()))) {
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        try {
                            return (Class<?>) actualTypeArguments[0];
                        } catch (Exception e) {
                            Cat.e(e);
                        }
                    }
                }
            }

            return null;
        }

        public synchronized T getResult() {
            return mResult;
        }
    }

    public interface PendingResultCallback<T> {
        public void onResponseArrived(T response);
    }
}
