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

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public class ConcealCipherTool implements CipherTool {

    protected final Crypto mCrypto;
    protected final Charset mCharset;
//    protected final HashTool mHashTool;
//
//    public ConcealCipherTool(KeyChain keyChain) {
//        this(keyChain);
//    }

    public ConcealCipherTool(KeyChain keyChain) {
        this(keyChain, Charset.forName("UTF-16"));
    }

    public ConcealCipherTool(KeyChain keyChain, Charset charset) {
        PRNGFixes.apply();

        mCrypto = new Crypto(keyChain, new SystemNativeCryptoLibrary());

        if (!mCrypto.isAvailable()) {
            throw new IllegalStateException();
        }

        mCharset = charset;
//        mHashTool = hashTool;
    }

    public Crypto getCrypto() {
        return mCrypto;
    }

//    @Override
//    public byte[] getHash(String clearText) {
//        return mHashTool.getHash(clearText);
//    }
//
//    @Override
//    public byte[] getHash(String clearText, int iterations) {
//        return mHashTool.getHash(clearText, iterations);
//    }
//
//    @Override
//    public String getHashString(String clearText) {
//        return mHashTool.getHashString(clearText);
//    }
//
//    @Override
//    public String getHashString(String clearText, int iterations) {
//        return mHashTool.getHashString(clearText, iterations);
//    }

    @Override
    public String decrypt(String cipherText) {
        byte[] decrypt = decrypt(Base64.decode(cipherText, Base64.NO_WRAP | Base64.URL_SAFE));
        return new String(decrypt, mCharset);
    }

    @Override
    public byte[] decrypt(byte[] cipherText) {
        try {
            return mCrypto.decrypt(cipherText, new Entity(""));
        } catch (KeyChainException | CryptoInitializationException | IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String encrypt(String clearText) {
        byte[] bytes = encrypt(clearText.getBytes(mCharset));
        return Base64.encodeToString(bytes, Base64.NO_WRAP | Base64.URL_SAFE);
    }

    @Override
    public byte[] encrypt(byte[] data) {
        try {
            return mCrypto.encrypt(data, new Entity(""));
        } catch (KeyChainException | CryptoInitializationException | IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
