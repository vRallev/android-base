package net.vrallev.android.base.util;

import android.app.AlarmManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.nfc.NfcManager;
import android.os.PowerManager;
import android.view.WindowManager;

/**
 * Helper class to give static access to Android services without having a {@link android.content.Context} instance.
 * 
 * @author Ralf Wondratschek
 *
 */
public final class AndroidServices {

	private static NfcManager sNfcManager;
	private static ConnectivityManager sConnectivityManager;
	private static WifiManager sWifiManager;
	private static AlarmManager sAlarmManager;
	private static WindowManager sWindowManager;
	private static PowerManager sPowerManager;
	private static PackageManager sPackageManager;
    private static AudioManager sAudioManager;
	
	private AndroidServices() {
		// no op
	}

	public static void init(Context context) {
		sConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		sWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		sAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		sWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		sPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		sPackageManager = context.getPackageManager();
		sNfcManager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        sAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }
	
	public static NfcManager getNfcManager() {
		if (sNfcManager == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return sNfcManager;
	}
	
	public static ConnectivityManager getConnectivityManager() {
		if (sConnectivityManager == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return sConnectivityManager;
	}
	
	public static WifiManager getWifiManager() {
		if (sWifiManager == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return sWifiManager;
	}
	
	public static AlarmManager getAlarmManager() {
		if (sAlarmManager == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return sAlarmManager;
	}
	
	public static WindowManager getWindowManager() {
		if (sWindowManager == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return sWindowManager;
	}
	
	public static PowerManager getPowerManager() {
		if (sPowerManager == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return sPowerManager;
	}
	
	public static PackageManager getPackageManager() {
		if (sPackageManager == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return sPackageManager;
	}

    public static AudioManager getAudioManager() {
        if (sAudioManager == null) {
            throw new NullPointerException("AndroidServices needs to be initialized first.");
        }

        return sAudioManager;
    }
}
