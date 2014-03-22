package net.vrallev.android.base.security.keychain.facebook;

import android.content.Context;

import com.facebook.crypto.keychain.KeyChain;
import com.facebook.crypto.keychain.SharedPrefsBackedKeyChain;

import net.vrallev.android.base.security.keychain.FacebookKeyChain;

/**
 * @author Ralf Wondratschek
 */
public class FacebookSharedPreferencesKeyChain extends FacebookKeyChain {

    private final Context mContext;

    public FacebookSharedPreferencesKeyChain(Context context) {
        mContext = context;
    }

    @Override
    public KeyChain createKeyChain() {
        return new SharedPrefsBackedKeyChain(mContext);
    }
}
