package net.vrallev.android.base.security;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public interface CipherTool {

//    public static final int DEFAULT_ITERATION_COUNT = 1;
//    public static final int DEFAULT_HASH_ITERATION_COUNT = 16;
//
//    @Deprecated
//    public byte[] getHash(String clearText);
//    @Deprecated
//    public byte[] getHash(String clearText, int iterations);
//    @Deprecated
//    public String getHashString(String clearText);
//    @Deprecated
//    public String getHashString(String clearText, int iterations);

    public String decrypt(String cipherText);
    public byte[] decrypt(byte[] cipherText);

    public String encrypt(String clearText);
    public byte[] encrypt(byte[] data);
}
