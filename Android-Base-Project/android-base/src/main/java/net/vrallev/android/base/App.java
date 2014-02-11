package net.vrallev.android.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;

import net.vrallev.android.base.security.CipherTool;
import net.vrallev.android.base.settings.SettingsMgr;
import net.vrallev.android.base.util.AndroidServices;
import net.vrallev.android.base.util.Cat;
import net.vrallev.android.base.util.DisplayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 
 * @author Ralf Wondratschek
 *
 */
@SuppressWarnings({"UnusedDeclaration", "deprecation"})
@Deprecated
public class App extends Application {

    private static App instance;

    private static Handler guiHandler;
    private static CipherTool cipherTool;
    private static SettingsMgr settingsMgr;

	/**
	 * @return The only instance at runtime.
	 */
    @Deprecated
	public static App getInstance() {
		return instance;
	}

	/**
	 * @return A {@link android.os.Handler}, which is prepared for the GUI Thread.
	 */
    @Deprecated
	public static Handler getGuiHandler() {
		return guiHandler;
	}

    @Deprecated
    public static void setGuiHandler(Handler handler) {
        guiHandler = handler;
    }

	/**
	 * @return A singleton to get access to the {@link android.content.SharedPreferences}.
	 */
    @Deprecated
	public static SettingsMgr getSettingsMgr() {
		return settingsMgr;
	}

    @Deprecated
    public static void setSettingsMgr(SettingsMgr settingsMgr) {
        App.settingsMgr = settingsMgr;
    }

    @Deprecated
    public static CipherTool getCipherTool() {
        return cipherTool;
    }

    @Deprecated
    public static void setCipherTool(CipherTool cipherTool) {
        App.cipherTool = cipherTool;
    }

    protected Activity mVisibleActivity;
    protected Activity mLastCreatedActivity;

    @Override
	public void onCreate() {
		instance = this;

        initializeCat();

        //noinspection deprecation
        AndroidServices.init(getApplicationContext());
        //noinspection deprecation
        DisplayUtils.init(getApplicationContext());

        //noinspection deprecation
        cipherTool = createCipherTool();
        //noinspection deprecation
        settingsMgr = createSettingsMgr();
        //noinspection deprecation
        guiHandler = createGuiHandler();

        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
		
		super.onCreate();
	}

    @Deprecated
    protected void initializeCat() {
        try {
            Field[] declaredFields = Class.forName(getPackageName() + ".BuildConfig").getDeclaredFields();
            for (Field field : declaredFields) {
                if (Modifier.isStatic(field.getModifiers()) && field.getName().endsWith("DEBUG")) {
                    Cat.setDefaultInstance(field.getBoolean(null));
                    return;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException e) {
            Cat.e(e);
        }
    }

    @Deprecated
    protected SettingsMgr createSettingsMgr() {
        return new SettingsMgr(this);
    }

    @Deprecated
    protected Handler createGuiHandler() {
        return new Handler();
    }

    @Deprecated
    protected CipherTool createCipherTool() {
        return null;
    }


    @Deprecated
    public Activity getVisibleActivity() {
        return mVisibleActivity;
    }

    @Deprecated
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

    @Deprecated
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
