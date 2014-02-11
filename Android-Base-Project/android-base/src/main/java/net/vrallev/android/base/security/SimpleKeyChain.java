package net.vrallev.android.base.security;

import com.facebook.crypto.cipher.NativeGCMCipher;
import com.facebook.crypto.exception.KeyChainException;
import com.facebook.crypto.keychain.KeyChain;
import com.facebook.crypto.mac.NativeMac;

import java.nio.charset.Charset;
import java.security.SecureRandom;

/**
 * @author Ralf Wondratschek
 */
public class SimpleKeyChain implements KeyChain {

    private final SecureRandom mSecureRandom;

    private final byte[] mCipherKey;
    private final byte[] mMacKey;

    public SimpleKeyChain(String passPhrase) {
        mSecureRandom = new SecureRandom();

        mCipherKey = generateKey(passPhrase, NativeGCMCipher.KEY_LENGTH);
        mMacKey = generateKey(passPhrase, NativeMac.KEY_LENGTH);
    }

    @Override
    public byte[] getCipherKey() throws KeyChainException {
        return mCipherKey;
    }

    @Override
    public byte[] getMacKey() throws KeyChainException {
        return mMacKey;
    }

    @Override
    public byte[] getNewIV() throws KeyChainException {
        byte[] iv = new byte[NativeGCMCipher.IV_LENGTH];
        mSecureRandom.nextBytes(iv);
        return iv;
    }

    @Override
    public void destroyKeys() {
        // do nothing
    }

    private static byte[] generateKey(String passPhrase, int length) {
        byte[] result = new byte[length];

        Charset charset = Charset.forName("UTF-16");
        byte[] initBytes = passPhrase.getBytes(charset);

        for (int i = 0; i < result.length; i++) {
            result[i] = initBytes[i % initBytes.length];
        }

        return result;
    }
}
