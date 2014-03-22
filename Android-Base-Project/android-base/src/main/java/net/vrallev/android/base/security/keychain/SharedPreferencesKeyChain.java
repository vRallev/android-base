package net.vrallev.android.base.security.keychain;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.security.SecureRandom;

/**
 * @author Ralf Wondratschek
 */
public class SharedPreferencesKeyChain implements BaseKeyChain {

    protected final SharedPreferences mSharedPreferences;
    private final SecureRandom mSecureRandom;
    private final int mLength;

    public SharedPreferencesKeyChain(Context context) {
        this(context, 24);
    }

    public SharedPreferencesKeyChain(Context context, int length) {
        mSharedPreferences = context.getSharedPreferences("crypto", Context.MODE_PRIVATE);
        mSecureRandom = new SecureRandom();
        mLength = length;
    }

    @Override
    public String getPassPhrase() {
        return getValue("passPhrase");
    }

    @Override
    public String getSalt() {
        return getValue("salt");
    }

    protected String getValue(String prefKey) {
        if (!mSharedPreferences.contains(prefKey)) {
            generateAndSaveKey(prefKey, mLength);
        }

        String prefValue = mSharedPreferences.getString(prefKey, prefKey);
        return new String(decodeFromPrefs(prefValue));
    }

    protected byte[] generateAndSaveKey(String pref, int length) {
        byte[] key = new byte[length];
        mSecureRandom.nextBytes(key);
        // Store the session key.
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(pref, encodeForPrefs(key));
        editor.commit();
        return key;
    }

    protected String encodeForPrefs(byte[] key) {
        if (key == null) {
            return null;
        }
        return Base64.encodeToString(key, Base64.DEFAULT);
    }

    protected byte[] decodeFromPrefs(String keyString) {
        if (keyString == null) {
            return null;
        }
        return Base64.decode(keyString, Base64.DEFAULT);
    }
}
