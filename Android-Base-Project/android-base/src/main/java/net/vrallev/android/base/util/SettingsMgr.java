package net.vrallev.android.base.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * 
 * @author Ralf Wondratschek
 * 
 *
 */
public class SettingsMgr {

	private final SharedPreferences mPreferences;

	public SettingsMgr(Context context) {
		this(context, -1);
	}
	
	public SettingsMgr(Context context, int... defaultPreferences) {
		if (defaultPreferences.length > 0) {
			for (int preference : defaultPreferences) {
				if (preference >= 0) {
					PreferenceManager.setDefaultValues(context, preference, true);
				}
			}
		}

        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}
	
	public boolean getBoolean(String key, boolean defaultValue) {
		return mPreferences.getBoolean(key, defaultValue);
	}

	public String getString(String key) {
		return getString(key, null);
	}
	
	public String getString(String key, String defaultValue) {
		return mPreferences.getString(key, defaultValue);
	}
	
	public int getInt(String key) {
		return getInt(key, -1);
	}
	
	public int getInt(String key, int defaultValue) {
		return mPreferences.getInt(key, defaultValue);
	}
	
	public Set<String> getStringSet(String key) {
		return getStringSet(key, null);
	}
	
	public Set<String> getStringSet(String key, Set<String> defaultValue) {
		return mPreferences.getStringSet(key, defaultValue);
	}
	
	public void putBoolean(final String key, final boolean value) {
        Editor editor = mPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
	
	public void putString(final String key, final String value) {
        Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
	
	public void putInt(final String key, final int value) {
        Editor editor = mPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
	
	public void putStringSet(final String key, final Set<String> value) {
        Editor editor = mPreferences.edit();
        editor.putStringSet(key, value);
        editor.apply();
	}
}
