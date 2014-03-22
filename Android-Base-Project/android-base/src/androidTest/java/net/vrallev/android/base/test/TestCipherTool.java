package net.vrallev.android.base.test;

import android.test.AndroidTestCase;

import com.facebook.crypto.keychain.KeyChain;
import com.facebook.crypto.keychain.SharedPrefsBackedKeyChain;

import net.vrallev.android.base.security.ConcealCipherTool;
import net.vrallev.android.base.security.keychain.facebook.FacebookSimpleKeyChain;

import java.io.IOException;

/**
 * @author Ralf Wondratschek
 */
public class TestCipherTool extends AndroidTestCase {

    public void testCipherTool() {
        runTest(new FacebookSimpleKeyChain("Hello World!"));
        runTest(new SharedPrefsBackedKeyChain(getContext()));
    }

    private void runTest(KeyChain keyChain) {
        ConcealCipherTool cipherTool = new ConcealCipherTool(keyChain);

        String clearText = "Hällö Wörld!;''''´´´´";
        String encrypt = cipherTool.encrypt(clearText);
        String decrypt = cipherTool.decrypt(encrypt);

        assertEquals(clearText, decrypt);

        try {
            cipherTool.decrypt(clearText);
            assertTrue(false); // don't reach this point
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertTrue(e.getCause() instanceof IOException);
        }

        encrypt += "a";
        try {
            cipherTool.decrypt(encrypt);
            assertTrue(false); // don't reach this point
        } catch (Exception e) {
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }
}
