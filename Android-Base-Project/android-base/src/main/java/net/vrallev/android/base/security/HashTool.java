package net.vrallev.android.base.security;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public class HashTool {

    public static final String DEFAULT_HASH_ALGORITHM = "SHA-512";

    private final int mDefaultHashIterations;
    private final MessageDigest mMessageDigest;

    public HashTool(int defaultHashIterations) throws NoSuchAlgorithmException {
        mDefaultHashIterations = defaultHashIterations;
        mMessageDigest = MessageDigest.getInstance(DEFAULT_HASH_ALGORITHM);
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
        byte[] hash = clearText.getBytes(Charset.defaultCharset());
        for (int i = 0; i < iterations; i++) {
            hash = mMessageDigest.digest(hash);
        }
        return hash;
    }

    public static String bin2hex(byte[] data) {
        // http://stackoverflow.com/questions/7166129/how-can-i-calculate-the-sha-256-hash-of-a-string-in-android
        return String.format("%0" + (data.length * 2) + 'x', new BigInteger(1, data));
    }
}
