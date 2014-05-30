package net.vrallev.android.base.security;

import android.util.Base64;

import java.nio.charset.Charset;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public class BouncyCastleCipherTool implements CipherTool {

    @SuppressWarnings("SpellCheckingInspection")
    public static final String DEFAULT_ALGORITHM = "PBEWITHSHA256AND256BITAES-CBC-BC";

    protected final Cipher mEncryptCipher;
    protected final Cipher mDecryptCipher;

    protected Charset mCharset;

    public BouncyCastleCipherTool(String passPhrase, String salt) {
        this(passPhrase, salt, 1);
    }

    public BouncyCastleCipherTool(String passPhrase, String salt, int iterations) {
        this(passPhrase, salt.getBytes(Charset.defaultCharset()), iterations);
    }

    public BouncyCastleCipherTool(String passPhrase, byte[] salt, int iterations) {
        this(DEFAULT_ALGORITHM, passPhrase, salt, iterations);
    }

    public BouncyCastleCipherTool(String algorithm, String passPhrase, byte[] salt, int iterations) {
        PRNGFixes.apply();

        try {
            PBEKeySpec pbeKeySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterations);
            SecretKey secretKey = SecretKeyFactory.getInstance(algorithm).generateSecret(pbeKeySpec);

            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterations);

            mEncryptCipher = Cipher.getInstance(secretKey.getAlgorithm());
            mEncryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            mDecryptCipher = Cipher.getInstance(secretKey.getAlgorithm());
            mDecryptCipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

            mCharset = Charset.defaultCharset();

        } catch (Exception e) {
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
            return mEncryptCipher.doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String decrypt(String cipherText) {
        byte[] bytes = decrypt(Base64.decode(cipherText, Base64.NO_WRAP));
        return new String(bytes);
    }

    @Override
    public byte[] decrypt(byte[] cipherText) {
        try {
            return mDecryptCipher.doFinal(cipherText);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
