package net.vrallev.android.base.security;

import android.util.Base64;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
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

    private final Cipher mEncryptCipher;
    private final Cipher mDecryptCipher;

    private final HashTool mHashTool;

    public BouncyCastleCipherTool(String passPhrase, String salt) {
        this(passPhrase, salt, DEFAULT_ITERATION_COUNT, DEFAULT_HASH_ITERATION_COUNT);
    }

    public BouncyCastleCipherTool(String passPhrase, String salt, int iterations, int hashIterations) {
        this(passPhrase, salt.getBytes(Charset.defaultCharset()), iterations, hashIterations);
    }

    public BouncyCastleCipherTool(String passPhrase, byte[] salt, int iterations, int hashIterations) {
        this(DEFAULT_ALGORITHM, passPhrase, salt, iterations, hashIterations);
    }

    public BouncyCastleCipherTool(String algorithm, String passPhrase, byte[] salt, int iterations, int hashIterations) {
        PRNGFixes.apply();

        try {
            PBEKeySpec pbeKeySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterations);
            SecretKey secretKey = SecretKeyFactory.getInstance(algorithm).generateSecret(pbeKeySpec);

            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterations);

            mEncryptCipher = Cipher.getInstance(secretKey.getAlgorithm());
            mEncryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            mDecryptCipher = Cipher.getInstance(secretKey.getAlgorithm());
            mDecryptCipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

            mHashTool = new HashTool(hashIterations);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String encrypt(String clearText) {
        try {
            byte[] bytes = mEncryptCipher.doFinal(clearText.getBytes());
            return Base64.encodeToString(bytes, Base64.NO_WRAP);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String decrypt(String cipherText) {
        try {
            byte[] bytes = mDecryptCipher.doFinal(Base64.decode(cipherText, Base64.NO_WRAP));
            return new String(bytes);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public byte[] getHash(String clearText) {
        return mHashTool.getHash(clearText);
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
    public byte[] getHash(String clearText, int iterations) {
        return mHashTool.getHash(clearText, iterations);
    }
}
