package net.vrallev.android.base.util;

import android.app.AlarmManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.nfc.NfcManager;
import android.os.PowerManager;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import net.vrallev.android.base.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ralf Wondratschek
 */
@Module(
        library = true,
        complete = false
)
public class AndroidServicesModule {

    @Provides
    @Singleton
    NfcManager provideNfcManager(@ForApplication Context context) {
        return (NfcManager) context.getSystemService(Context.NFC_SERVICE);
    }

    @Provides
    @Singleton
    ConnectivityManager provideConnectivityManager(@ForApplication Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    WifiManager provideWifiManager(@ForApplication Context context) {
        return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    @Provides
    @Singleton
    AlarmManager provideAlarmManager(@ForApplication Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Provides
    @Singleton
    WindowManager provideWindowManager(@ForApplication Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    @Provides
    @Singleton
    PowerManager providePowerManager(@ForApplication Context context) {
        return (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    }

    @Provides
    @Singleton
    PackageManager providePackageManager(@ForApplication Context context) {
        return context.getPackageManager();
    }

    @Provides
    @Singleton
    AudioManager provideAudioManager(@ForApplication Context context) {
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @Provides
    @Singleton
    InputMethodManager provideInputMethodManager(@ForApplication Context context) {
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Provides
    @Singleton
    LocationManager provideLocationManager(@ForApplication Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @Singleton
    SensorManager provideSensorManager(@ForApplication Context context) {
        return (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }
}
