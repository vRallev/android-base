package net.vrallev.android.base;

import android.app.Activity;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ralf Wondratschek
 */
@Module(
        library = true,
        addsTo = BaseAppModule.class
)
public class BaseActivityModule {

    private final Activity mActivity;

    public BaseActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @Singleton
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @Singleton
    @ForActivity
    Context provideActivityContext() {
        return mActivity;
    }

    @SuppressWarnings("deprecation")
    @Provides
    @Singleton
    BaseActivity provideBaseActivity() {
        if (mActivity instanceof BaseActivity) {
            return (BaseActivity) mActivity;
        } else {
            return null;
        }
    }

    @Provides
    @Singleton
    BaseActivitySupport provideBaseActivitySupport() {
        if (mActivity instanceof BaseActivitySupport) {
            return (BaseActivitySupport) mActivity;
        } else {
            return null;
        }
    }
}
