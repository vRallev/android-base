package net.vrallev.android.base.settings;

import android.content.Context;

import net.vrallev.android.base.security.CipherTool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public class SettingsMgrCipher extends SettingsMgr {

    protected final CipherTool mCipherTool;

    public SettingsMgrCipher(Context context, CipherTool cipherTool) {
        this(context, cipherTool, -1);
    }

    public SettingsMgrCipher(Context context, CipherTool cipherTool, int... defaultPreferences) {
        super(context, defaultPreferences);
        mCipherTool = cipherTool;
    }

    protected String hashedKey(String key) {
        return mCipherTool.getHashString(key);
    }

    @Override
    public String getString(String key, String defaultValue) {
        key = hashedKey(key);
        if (mPreferences.contains(key)) {
            return mCipherTool.decrypt(mPreferences.getString(key, defaultValue));
        } else {
            return defaultValue;
        }
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(getString(key, String.valueOf(defaultValue)));
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return Integer.parseInt(getString(key, String.valueOf(defaultValue)));
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        key = hashedKey(key);
        if (mPreferences.contains(key)) {
            Set<String> stringSet = mPreferences.getStringSet(key, defaultValue);
            Set<String> result = new HashSet<>(stringSet.size());

            for (String string : stringSet) {
                result.add(mCipherTool.decrypt(string));
            }
            return result;

        } else {
            return defaultValue;
        }
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return Float.parseFloat(getString(key, String.valueOf(defaultValue)));
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return Long.parseLong(getString(key, String.valueOf(defaultValue)));
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        return Double.parseDouble(getString(key, String.valueOf(defaultValue)));
    }

    @Override
    public void putBoolean(String key, boolean value) {
        putString(key, String.valueOf(value));
    }

    @Override
    public void putString(String key, String value) {
        super.putString(hashedKey(key), mCipherTool.encrypt(value));
    }

    @Override
    public void putInt(String key, int value) {
        putString(key, String.valueOf(value));
    }

    @Override
    public void putStringSet(String key, Set<String> value) {
        Set<String> resultSet = new HashSet<>(value.size());
        for (String string : value) {
            resultSet.add(mCipherTool.encrypt(string));
        }

        super.putStringSet(hashedKey(key), resultSet);
    }

    @Override
    public void putFloat(String key, float value) {
        putString(key, String.valueOf(value));
    }

    @Override
    public void putLong(String key, long value) {
        putString(key, String.valueOf(value));
    }

    @Override
    public void putDouble(String key, double value) {
        putString(key, String.valueOf(value));
    }

    @Override
    public void remove(String key) {
        super.remove(hashedKey(key));
    }
}
