package net.vrallev.android.base;

import android.content.Context;
import android.os.HandlerThread;
import android.os.Looper;

import net.vrallev.android.base.security.CipherTool;
import net.vrallev.android.base.security.SecurityModule;
import net.vrallev.android.base.settings.SettingsMgr;
import net.vrallev.android.base.settings.SettingsMgrMixed;
import net.vrallev.android.base.util.AndroidServicesModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ralf Wondratschek
 */
@Module(
        library = true,
        includes = {
                AndroidServicesModule.class,
                SecurityModule.class
        }
)
public class BaseAppModule {

    private final BaseApp mBaseApp;

    public BaseAppModule(BaseApp baseApp) {
        mBaseApp = baseApp;
    }

    @Provides
    @Singleton
    BaseApp provideApp() {
        return mBaseApp;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideAppContext() {
        return mBaseApp;
    }

    @Provides
    @Singleton
    @Named("background")
    Looper provideBackgroundLooper() {
        HandlerThread thread = new HandlerThread("Background");
        thread.start();
        return thread.getLooper();
    }

    @Provides
    @Singleton
    @Named("main")
    Looper provideMainLooper() {
        return Looper.getMainLooper();
    }

    @Provides
    @Singleton
    SettingsMgr provideSettingsMgr(@ForApplication Context context, CipherTool cipherTool) {
        return new SettingsMgrMixed(context, cipherTool);
    }
}
