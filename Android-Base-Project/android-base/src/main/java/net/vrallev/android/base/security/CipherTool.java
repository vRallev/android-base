package net.vrallev.android.base.security;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public interface CipherTool {

    public String decrypt(String cipherText);
    public byte[] decrypt(byte[] cipherText);

    public String encrypt(String clearText);
    public byte[] encrypt(byte[] data);
}
