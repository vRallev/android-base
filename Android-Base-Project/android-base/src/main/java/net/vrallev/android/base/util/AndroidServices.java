package net.vrallev.android.base.util;

import android.app.AlarmManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.nfc.NfcManager;
import android.os.PowerManager;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Helper class to give static access to Android services without having a {@link android.content.Context} instance.
 * 
 * @author Ralf Wondratschek
 *
 */
public final class AndroidServices {

	private AndroidServices() {
		// no op
	}

    private static Context context;

	public static void init(Context context) {
        AndroidServices.context = context.getApplicationContext();
    }
	
	public static NfcManager getNfcManager() {
		if (context == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return (NfcManager) context.getSystemService(Context.NFC_SERVICE);
	}
	
	public static ConnectivityManager getConnectivityManager() {
		if (context == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	
	public static WifiManager getWifiManager() {
		if (context == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	}
	
	public static AlarmManager getAlarmManager() {
		if (context == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	}
	
	public static WindowManager getWindowManager() {
		if (context == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	}
	
	public static PowerManager getPowerManager() {
		if (context == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	}
	
	public static PackageManager getPackageManager() {
		if (context == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return context.getPackageManager();
	}

    public static AudioManager getAudioManager() {
        if (context == null) {
            throw new NullPointerException("AndroidServices needs to be initialized first.");
        }

        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public static InputMethodManager getInputMethodManager() {
        if (context == null) {
            throw new NullPointerException("AndroidServices needs to be initialized first.");
        }

        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static LocationManager getLocationManager() {
        if (context == null) {
            throw new NullPointerException("AndroidServices needs to be initialized first.");
        }
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }
}
