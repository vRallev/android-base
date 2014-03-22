package net.vrallev.android.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import net.vrallev.android.base.util.Cat;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;

/**
 * 
 * @author Ralf Wondratschek
 *
 */
@SuppressWarnings("UnusedDeclaration")
public class BaseApp extends Application {

    private static BaseApp instance;

	/**
	 * @return The only instance at runtime.
	 */
	public static BaseApp getInstance() {
		return instance;
	}

    protected Activity mVisibleActivity;
    protected Activity mLastCreatedActivity;

    protected ObjectGraph mObjectGraph;

    @Override
	public void onCreate() {
		instance = this;

        initializeCat();

        List<Object> modules = new ArrayList<>();
        addModules(modules);

        mObjectGraph = ObjectGraph.create(modules.toArray());

        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
		
		super.onCreate();
	}

    public ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }

    protected void addModules(List<Object> modules) {
        modules.add(new BaseAppModule(this));
    }

    public void inject(Object object) {
        mObjectGraph.inject(object);
    }

    protected void initializeCat() {
        try {
            Class<?> buildConfigClass = getBuildConfigClass(getPackageName());
            if (buildConfigClass != null) {
                Field[] declaredFields = buildConfigClass.getDeclaredFields();
                for (Field field : declaredFields) {
                    if (Modifier.isStatic(field.getModifiers()) && field.getName().endsWith("DEBUG")) {
                        Cat.setDefaultInstance(field.getBoolean(null));
                        return;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            Cat.e(e);
        }
    }

    protected static Class<?> getBuildConfigClass(String packageName) {
        if (packageName == null || packageName.isEmpty()) {
            return null;
        }

        try {
            return Class.forName(packageName + ".BuildConfig");
        } catch (ClassNotFoundException e) {
            return getBuildConfigClass(packageName.substring(0, packageName.lastIndexOf(".")));
        }
    }

    public Activity getVisibleActivity() {
        return mVisibleActivity;
    }

    public Activity getLastCreatedActivity() {
        return mLastCreatedActivity;
    }

    private ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new ActivityLifecycleCallbacksAdapter() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            mLastCreatedActivity = activity;
        }

        @Override
        public void onActivityStarted(Activity activity) {
            mVisibleActivity = activity;
            mLastCreatedActivity = activity;
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (mVisibleActivity == activity) {
                mVisibleActivity = null;
            }
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (mLastCreatedActivity == activity) {
                mLastCreatedActivity = null;
            }
        }
    };

    public static abstract class ActivityLifecycleCallbacksAdapter implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}
        @Override
        public void onActivityStarted(Activity activity) {}
        @Override
        public void onActivityResumed(Activity activity) {}
        @Override
        public void onActivityPaused(Activity activity) {}
        @Override
        public void onActivityStopped(Activity activity) {}
        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
        @Override
        public void onActivityDestroyed(Activity activity) {}
    }
}
