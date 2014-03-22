package net.vrallev.android.base.security.keychain;

/**
 * @author Ralf Wondratschek
 */
public interface BaseKeyChain {

    public String getPassPhrase();
    public String getSalt();

}
