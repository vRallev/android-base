package net.vrallev.android.base.security;

import android.util.Base64;

import com.facebook.crypto.Crypto;
import com.facebook.crypto.Entity;
import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.exception.KeyChainException;
import com.facebook.crypto.keychain.KeyChain;
import com.facebook.crypto.util.SystemNativeCryptoLibrary;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public class ConcealCipherTool implements CipherTool {

    private final Crypto mCrypto;
    private final Charset mCharset;
    private final HashTool mHashTool;

    public ConcealCipherTool(KeyChain keyChain) {
        this(keyChain, DEFAULT_HASH_ITERATION_COUNT);
    }

    public ConcealCipherTool(KeyChain keyChain, int hashIterations) {
        PRNGFixes.apply();

        mCrypto = new Crypto(keyChain, new SystemNativeCryptoLibrary());
        mCharset = Charset.forName("UTF-16");

        if (!mCrypto.isAvailable()) {
            throw new IllegalStateException();
        }

        try {
            mHashTool = new HashTool(hashIterations);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Crypto getCrypto() {
        return mCrypto;
    }

    @Override
    public byte[] getHash(String clearText) {
        return mHashTool.getHash(clearText);
    }

    @Override
    public byte[] getHash(String clearText, int iterations) {
        return mHashTool.getHash(clearText, iterations);
    }

    @Override
    public String getHashString(String clearText) {
        return mHashTool.getHashString(clearText);
    }

    @Override
    public String getHashString(String clearText, int iterations) {
        return mHashTool.getHashString(clearText, iterations);
    }

    @Override
    public String decrypt(String cipherText) {
        try {
            byte[] bytes = mCrypto.decrypt(Base64.decode(cipherText, Base64.NO_WRAP), new Entity(""));
            return new String(bytes, mCharset);
        } catch (KeyChainException | CryptoInitializationException | IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String encrypt(String clearText) {
        try {
            byte[] bytes = mCrypto.encrypt(clearText.getBytes(mCharset), new Entity(""));
            return Base64.encodeToString(bytes, Base64.NO_WRAP);
        } catch (KeyChainException | CryptoInitializationException | IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
