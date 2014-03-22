package net.vrallev.android.base.security.keychain;

import com.facebook.crypto.keychain.KeyChain;

/**
 * @author Ralf Wondratschek
 */
public abstract class FacebookKeyChain implements BaseKeyChain {

    public abstract KeyChain createKeyChain();

    @Override
    public String getPassPhrase() {
        return null;
    }

    @Override
    public String getSalt() {
        return null;
    }
}
