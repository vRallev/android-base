package net.vrallev.android.base.security;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public class HashTool {

    public static final String DEFAULT_HASH_ALGORITHM = "SHA-512";
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    protected int mDefaultHashIterations;
    protected MessageDigest mMessageDigest;
    protected Charset mCharset;

    public HashTool(int defaultHashIterations) {
        this(defaultHashIterations, DEFAULT_HASH_ALGORITHM, UTF_8);
    }

    public HashTool(int defaultHashIterations, String algorithm, Charset charset) {
        PRNGFixes.apply();

        mDefaultHashIterations = defaultHashIterations;
        mCharset = charset;
        try {
            mMessageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public byte[] getHash(String clearText) {
        return getHash(clearText, mDefaultHashIterations);
    }

    public byte[] getHash(byte[] data) {
        return getHash(data, mDefaultHashIterations);
    }

    public String getHashString(String clearText) {
        return getHashString(clearText.getBytes(mCharset));
    }

    public String getHashString(byte[] data) {
        return getHashString(data, mDefaultHashIterations);
    }

    public String getHashString(String clearText, int iterations) {
        return getHashString(clearText.getBytes(mCharset), iterations);
    }

    public String getHashString(byte[] data, int iterations) {
        return EncodingHelper.bin2hex(getHash(data, iterations));
    }

    public byte[] getHash(String clearText, int iterations) {
        return getHash(clearText.getBytes(mCharset), iterations);
    }

    public byte[] getHash(byte[] data, int iterations) {
        iterations = Math.max(1, iterations);
        for (int i = 0; i < iterations; i++) {
            data = mMessageDigest.digest(data);
        }
        return data;
    }
}
