package net.vrallev.android.base.settings;

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
@SuppressWarnings("UnusedDeclaration")
public class SettingsMgr {

    protected final Context mContext;
    protected final SharedPreferences mPreferences;

	public SettingsMgr(Context context) {
		this(context, -1);
	}
	
	public SettingsMgr(Context context, int... defaultPreferences) {
        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        setDefaultValues(defaultPreferences);
	}

    public void setDefaultValues(int... defaultPreferences) {
        if (defaultPreferences.length > 0) {
            for (int preference : defaultPreferences) {
                if (preference >= 0) {
                    PreferenceManager.setDefaultValues(mContext, preference, true);
                }
            }
        }
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

    public float getFloat(String key) {
        return getFloat(key, -1f);
    }

    public float getFloat(String key, float defaultValue) {
        return mPreferences.getFloat(key, defaultValue);
    }

    public long getLong(String key) {
        return getLong(key, -1l);
    }

    public long getLong(String key, long defaultValue) {
        return mPreferences.getLong(key, defaultValue);
    }

    public double getDouble(String key) {
        return getDouble(key, -1d);
    }

    public double getDouble(String key, double defaultValue) {
        if (!mPreferences.contains(key)) {
            return defaultValue;
        }
        return Double.longBitsToDouble(getLong(key));
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

    public void putFloat(final String key, final float value) {
        Editor editor = mPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
	}

    public void putLong(final String key, final long value) {
        Editor editor = mPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
	}

    public void putDouble(final String key, final double value) {
        putLong(key, Double.doubleToRawLongBits(value));
	}

    public void remove(String key) {
        mPreferences.edit().remove(key).apply();
    }
}
