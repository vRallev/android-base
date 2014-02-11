package net.vrallev.android.base.security;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public interface CipherTool {

    public static final int DEFAULT_ITERATION_COUNT = 1;
    public static final int DEFAULT_HASH_ITERATION_COUNT = 16;

    public byte[] getHash(String clearText);
    public byte[] getHash(String clearText, int iterations);
    public String getHashString(String clearText);
    public String getHashString(String clearText, int iterations);

    public String decrypt(String cipherText);
    public String encrypt(String clearText);
}
