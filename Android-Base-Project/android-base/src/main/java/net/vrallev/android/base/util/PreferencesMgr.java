package net.vrallev.android.base.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Ralf Wondratschek
 * 
 *
 */
public class PreferencesMgr {
	
	private static final int SHUTDOWN_EXECUTOR_SERVICE = 594940;
	private static final long SHUTDOWN_DELAY = 2000;
	
	private final SharedPreferences mPreferences;
	private final MyHandler mHandler;
	
	private ExecutorService mExecutorService;
	private List<Future<?>> mTasks;
	
	public PreferencesMgr(Context context) {
		this(context, -1);
	}
	
	public PreferencesMgr(Context context, int... defaultPreferences) {
		if (defaultPreferences.length > 0) {
			for (int preference : defaultPreferences) {
				if (preference >= 0) {
					PreferenceManager.setDefaultValues(context, preference, true);
				}
			}
		}
		mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		
		HandlerThread handlerThread = new HandlerThread(getClass().getName());
		handlerThread.start();
		mHandler = new MyHandler(handlerThread.getLooper(), this);
		
		mTasks = new ArrayList<Future<?>>();
	}

	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}
	
	public boolean getBoolean(String key, boolean defaultValue) {
		waitForPutFinish();
		return mPreferences.getBoolean(key, defaultValue);
	}

	public String getString(String key) {
		return getString(key, null);
	}
	
	public String getString(String key, String defaultValue) {
		waitForPutFinish();
		return mPreferences.getString(key, defaultValue);
	}
	
	public int getInt(String key) {
		return getInt(key, -1);
	}
	
	public int getInt(String key, int defaultValue) {
		waitForPutFinish();
		return mPreferences.getInt(key, defaultValue);
	}
	
	public Set<String> getStringSet(String key) {
		return getStringSet(key, null);
	}
	
	public Set<String> getStringSet(String key, Set<String> defaultValue) {
		waitForPutFinish();
		return mPreferences.getStringSet(key, defaultValue);
	}
	
	public void putBoolean(final String key, final boolean value) {
		postToBackgroundThread(new Runnable() {
			@Override
			public void run() {
				Editor editor = mPreferences.edit();
				editor.putBoolean(key, value);
				editor.commit();
			}
		});
	}
	
	public void putString(final String key, final String value) {
		postToBackgroundThread(new Runnable() {
			@Override
			public void run() {
				Editor editor = mPreferences.edit();
				editor.putString(key, value);
				editor.commit();
			}
		});
	}
	
	public void putInt(final String key, final int value) {
		postToBackgroundThread(new Runnable() {
			@Override
			public void run() {
				Editor editor = mPreferences.edit();
				editor.putInt(key, value);
				editor.commit();
			}
		});
	}
	
	public void putStringSet(final String key, final Set<String> value) {
		postToBackgroundThread(new Runnable() {
			@Override
			public void run() {
				Editor editor = mPreferences.edit();
				editor.putStringSet(key, value);
				editor.commit();
			}
		});
	}
	
	private void postToBackgroundThread(Runnable runnable) {
		if (mExecutorService != null) {
			if (mExecutorService.isShutdown() && !mExecutorService.isTerminated()) {
				try {
					mExecutorService.awaitTermination(1000L, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					Lo.error(e);
				}
			}
			
			if (mExecutorService.isShutdown()) {
				mExecutorService = null;
			}
		}
		
		if (mExecutorService == null) {
			mExecutorService = Executors.newSingleThreadExecutor();
		}
		
		mTasks.add(mExecutorService.submit(runnable));
		
		mHandler.removeMessages(SHUTDOWN_EXECUTOR_SERVICE);
        mHandler.setPreferencesMgr(this);
		mHandler.sendEmptyMessageDelayed(SHUTDOWN_EXECUTOR_SERVICE, SHUTDOWN_DELAY);
	}
	
	private void waitForPutFinish() {
		while(!mTasks.isEmpty()) {
			try {
				mTasks.get(0).get(1000L, TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				Lo.error(e);
			}
			mTasks.remove(0);
		}
	}
	
	private static class MyHandler extends Handler {
		/*
		 * Must be static, otherwise leaks may occur.
		 */

        private WeakReference<PreferencesMgr> mHolder;
		
		public MyHandler(Looper looper, PreferencesMgr mgr) {
			super(looper);
            setPreferencesMgr(mgr);
		}

        public void setPreferencesMgr(PreferencesMgr mgr) {
            mHolder = new WeakReference<PreferencesMgr>(mgr);
        }

		@Override
		public void handleMessage(Message msg) {
			PreferencesMgr preferencesMgr = mHolder.get();
			if (preferencesMgr != null && preferencesMgr.mExecutorService != null) {
				preferencesMgr.mExecutorService.shutdown();
			}
		}
	}
}
