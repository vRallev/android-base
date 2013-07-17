package net.vrallev.android.base.util;

import android.app.AlarmManager;
import android.content.Context;
import android.content.pm.PackageManager;
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

	private static NfcManager nfcManager;
	private static ConnectivityManager connectivityManager;
	private static WifiManager wifiManager;
	private static AlarmManager alarmManager;
	private static WindowManager windowManager;
	private static PowerManager powerManager;
	private static PackageManager packageManager;
	
	private AndroidServices() {
		// no op
	}

	public static void init(Context context) {
		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		packageManager = context.getPackageManager();
		nfcManager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
	}
	
	public static NfcManager getNfcManager() {
		if (nfcManager == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return nfcManager;
	}
	
	public static ConnectivityManager getConnectivityManager() {
		if (connectivityManager == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return connectivityManager;
	}
	
	public static WifiManager getWifiManager() {
		if (wifiManager == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return wifiManager;
	}
	
	public static AlarmManager getAlarmManager() {
		if (alarmManager == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return alarmManager;
	}
	
	public static WindowManager getWindowManager() {
		if (windowManager == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return windowManager;
	}
	
	public static PowerManager getPowerManager() {
		if (powerManager == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return powerManager;
	}
	
	public static PackageManager getPackageManager() {
		if (packageManager == null) {
			throw new NullPointerException("AndroidServices needs to be initialized first.");
		}
		
		return packageManager;
	}
}
