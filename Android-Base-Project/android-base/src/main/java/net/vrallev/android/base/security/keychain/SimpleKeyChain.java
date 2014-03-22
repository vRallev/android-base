package net.vrallev.android.base.security.keychain;

/**
 * @author Ralf Wondratschek
 */
public class SimpleKeyChain implements BaseKeyChain {

    private final String mPassPhrase;
    private final String mSalt;

    public SimpleKeyChain(String passPhrase, String salt) {
        mPassPhrase = passPhrase;
        mSalt = salt;
    }

    @Override
    public String getPassPhrase() {
        return mPassPhrase;
    }

    @Override
    public String getSalt() {
        return mSalt;
    }
}
