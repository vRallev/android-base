package net.vrallev.android.base;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Debug;
import android.os.Handler;
import android.widget.Toast;

import net.vrallev.android.base.util.AndroidServices;
import net.vrallev.android.base.util.DisplayUtils;
import net.vrallev.android.base.util.Lo;
import net.vrallev.android.base.util.PreferencesMgr;

/**
 * 
 * @author Ralf Wondratschek
 *
 */
public class App extends Application {

    public static final boolean DEBUG_CONNECTED = Debug.isDebuggerConnected();
	
	private static App instance;
	private static Handler guiHandler;
	private static PreferencesMgr preferencesMgr;
	
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

	/**
	 * @return A singleton to get access to the {@link android.content.SharedPreferences}.
	 */
	public static PreferencesMgr getPreferencesMgr() {
		return preferencesMgr;
	}
	
	@Override
	public void onCreate() {
		// debug = (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
		instance = this;
		
		Lo.setInstance(new Lo(getPackageName(), DEBUG_CONNECTED));
		
		AndroidServices.init(getApplicationContext());
        DisplayUtils.init(getApplicationContext());

        preferencesMgr = new PreferencesMgr(this);
		
		guiHandler = new Handler();
		
		super.onCreate();
	}
}
