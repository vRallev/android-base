package net.vrallev.android.base.security;

import android.content.Context;

import com.facebook.crypto.keychain.KeyChain;
import com.facebook.crypto.keychain.SharedPrefsBackedKeyChain;

import net.vrallev.android.base.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
@Module(
        library = true,
        complete = false
)
public class SecurityModule {

    @Provides
    @Singleton
    KeyChain provideKeyChain(@ForApplication Context context) {
        return new SharedPrefsBackedKeyChain(context);
    }

    @Provides
    @Singleton
    CipherTool provideCipherTool(KeyChain keyChain) {
        return new ConcealCipherTool(keyChain);
    }
}
