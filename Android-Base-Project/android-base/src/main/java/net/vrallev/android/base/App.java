package net.vrallev.android.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;

import net.vrallev.android.base.security.CipherTool;
import net.vrallev.android.base.settings.SettingsMgr;
import net.vrallev.android.base.util.AndroidServices;
import net.vrallev.android.base.util.DisplayUtils;

/**
 * 
 * @author Ralf Wondratschek
 *
 */
@SuppressWarnings("UnusedDeclaration")
public class App extends Application {

	private static App instance;
	private static Handler guiHandler;
	private static SettingsMgr settingsMgr;
    private static CipherTool cipherTool;
	
	/**
	 * @return The only instance at runtime.
	 */
	public static App getInstance() {
		return instance;
	}

	/**
	 * @return A {@link android.os.Handler}, which is prepared for the GUI Thread.
	 */
	public static Handler getGuiHandler() {
		return guiHandler;
	}

    public static void setGuiHandler(Handler handler) {
        guiHandler = handler;
    }

	/**
	 * @return A singleton to get access to the {@link android.content.SharedPreferences}.
	 */
	public static SettingsMgr getSettingsMgr() {
		return settingsMgr;
	}

    public static void setSettingsMgr(SettingsMgr settingsMgr) {
        App.settingsMgr = settingsMgr;
    }

    public static CipherTool getCipherTool() {
        return cipherTool;
    }

    public static void setCipherTool(CipherTool cipherTool) {
        App.cipherTool = cipherTool;
    }

    private Activity mVisibleActivity;
    private Activity mLastCreatedActivity;

    @Override
	public void onCreate() {
		instance = this;

		AndroidServices.init(getApplicationContext());
        DisplayUtils.init(getApplicationContext());

        cipherTool = createCipherTool();
        settingsMgr = createSettingsMgr();
		guiHandler = createGuiHandler();

        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
		
		super.onCreate();
	}

    protected SettingsMgr createSettingsMgr() {
        return new SettingsMgr(this);
    }

    protected Handler createGuiHandler() {
        return new Handler();
    }

    protected CipherTool createCipherTool() {
        return null;
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
