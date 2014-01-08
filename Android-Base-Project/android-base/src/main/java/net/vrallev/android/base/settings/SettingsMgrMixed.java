package net.vrallev.android.base.settings;

import android.content.Context;

import net.vrallev.android.base.security.CipherTool;

import java.util.Set;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public class SettingsMgrMixed extends SettingsMgrCipher {

    private final SettingsMgr mDefaultSettingsMgr;

    public SettingsMgrMixed(Context context, CipherTool cipherTool) {
        this(context, cipherTool, -1);
    }

    public SettingsMgrMixed(Context context, CipherTool cipherTool, int... defaultPreferences) {
        super(context, cipherTool, defaultPreferences);
        mDefaultSettingsMgr = new SettingsMgr(context);
    }

    @Override
    public String getString(String key, String defaultValue) {
        if (mPreferences.contains(key)) {
            return mDefaultSettingsMgr.getString(key, defaultValue);
        } else {
            return super.getString(key, defaultValue);
        }
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        if (mPreferences.contains(key)) {
            return mDefaultSettingsMgr.getBoolean(key, defaultValue);
        } else {
            return super.getBoolean(key, defaultValue);
        }
    }

    @Override
    public int getInt(String key, int defaultValue) {
        if (mPreferences.contains(key)) {
            return mDefaultSettingsMgr.getInt(key, defaultValue);
        } else {
            return super.getInt(key, defaultValue);
        }
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        if (mPreferences.contains(key)) {
            return mDefaultSettingsMgr.getStringSet(key, defaultValue);
        } else {
            return super.getStringSet(key, defaultValue);
        }
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        if (mPreferences.contains(key)) {
            return mDefaultSettingsMgr.getFloat(key, defaultValue);
        } else {
            return super.getFloat(key, defaultValue);
        }
    }

    @Override
    public long getLong(String key, long defaultValue) {
        if (mPreferences.contains(key)) {
            return mDefaultSettingsMgr.getLong(key, defaultValue);
        } else {
            return super.getLong(key, defaultValue);
        }
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        if (mPreferences.contains(key)) {
            return mDefaultSettingsMgr.getDouble(key, defaultValue);
        } else {
            return super.getDouble(key, defaultValue);
        }
    }

    @Override
    public void putBoolean(String key, boolean value) {
        if (mPreferences.contains(key)) {
            mDefaultSettingsMgr.putBoolean(key, value);
        } else {
            super.putBoolean(key, value);
        }
    }

    @Override
    public void putString(String key, String value) {
        if (mPreferences.contains(key)) {
            mDefaultSettingsMgr.putString(key, value);
        } else {
            super.putString(key, value);
        }
    }

    @Override
    public void putInt(String key, int value) {
        if (mPreferences.contains(key)) {
            mDefaultSettingsMgr.putInt(key, value);
        } else {
            super.putInt(key, value);
        }
    }

    @Override
    public void putStringSet(String key, Set<String> value) {
        if (mPreferences.contains(key)) {
            mDefaultSettingsMgr.putStringSet(key, value);
        } else {
            super.putStringSet(key, value);
        }
    }

    @Override
    public void putFloat(String key, float value) {
        if (mPreferences.contains(key)) {
            mDefaultSettingsMgr.putFloat(key, value);
        } else {
            super.putFloat(key, value);
        }
    }

    @Override
    public void putLong(String key, long value) {
        if (mPreferences.contains(key)) {
            mDefaultSettingsMgr.putLong(key, value);
        } else {
            super.putLong(key, value);
        }
    }

    @Override
    public void putDouble(String key, double value) {
        if (mPreferences.contains(key)) {
            mDefaultSettingsMgr.putDouble(key, value);
        } else {
            super.putDouble(key, value);
        }
    }

    @Override
    public void remove(String key) {
        if (mPreferences.contains(key)) {
            mDefaultSettingsMgr.remove(key);
        } else {
            super.remove(key);
        }
    }
}
