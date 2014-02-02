package net.vrallev.android.base.security;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public class CipherTool {

    @SuppressWarnings("SpellCheckingInspection")
    private static final String DEFAULT_ALGORITHM = "PBEWITHSHA256AND256BITAES-CBC-BC";
    private static final String DEFAULT_HASH_ALGORITHM = "SHA-512";

    private static final String UTF_8 = "UTF-8";

    private final Cipher mEncryptCipher;
    private final Cipher mDecryptCipher;

    private final MessageDigest mMessageDigest;
    private final int mDefaultHashIterations;

    public CipherTool(String passPhrase, String salt, int iterations, int hashIterations) {
        this(passPhrase, salt.getBytes(), iterations, hashIterations);
    }

    public CipherTool(String passPhrase, byte[] salt, int iterations, int hashIterations) {
        PRNGFixes.apply();

        try {
            PBEKeySpec pbeKeySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterations);
            SecretKey secretKey = SecretKeyFactory.getInstance(DEFAULT_ALGORITHM).generateSecret(pbeKeySpec);

            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterations);

            mEncryptCipher = Cipher.getInstance(secretKey.getAlgorithm());
            mEncryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            mDecryptCipher = Cipher.getInstance(secretKey.getAlgorithm());
            mDecryptCipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

            mMessageDigest = MessageDigest.getInstance(DEFAULT_HASH_ALGORITHM);
            mDefaultHashIterations = Math.max(1, hashIterations);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String encrypt(String clearText) {
        try {
            byte[] bytes = mEncryptCipher.doFinal(clearText.getBytes());
            return Base64.encodeToString(bytes, Base64.NO_WRAP);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String decrypt(String cipherText) {
        try {
            byte[] bytes = mDecryptCipher.doFinal(Base64.decode(cipherText, Base64.NO_WRAP));
            return new String(bytes);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public byte[] getHash(String clearText) {
        return getHash(clearText, mDefaultHashIterations);
    }

    public String getHashString(String clearText) {
        return getHashString(clearText, mDefaultHashIterations);
    }

    public String getHashString(String clearText, int iterations) {
        return bin2hex(getHash(clearText, iterations));
    }

    public byte[] getHash(String clearText, int iterations) {
        iterations = Math.max(1, iterations);
        try {
            byte[] hash = clearText.getBytes(UTF_8);
            for (int i = 0; i < iterations; i++) {
                hash = mMessageDigest.digest(hash);
            }
            return hash;

        } catch (UnsupportedEncodingException e) {
            // won't happen
            return null;
        }
    }

    public static String bin2hex(byte[] data) {
        // http://stackoverflow.com/questions/7166129/how-can-i-calculate-the-sha-256-hash-of-a-string-in-android
        return String.format("%0" + (data.length * 2) + 'x', new BigInteger(1, data));
    }
}
