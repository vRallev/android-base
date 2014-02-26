package net.vrallev.android.base.util;

import android.content.Context;
import android.net.NetworkInfo;

/**
 * Helper class for utility network operations.
 *
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
@Deprecated
public class NetworkUtils {

    /**
     * Check whether a network connection is available or not.
     *
     * @param context Can be any {@link android.content.Context}. It's used to receive a {@link android.net.ConnectivityManager} instance.
     * @return {@code true}, if a connection is available, {@code false} otherwise.
     */
    @Deprecated
    public static boolean isNetworkAvailable(Context context) {
        //noinspection deprecation
        NetworkInfo activeNetworkInfo = AndroidServices.getConnectivityManager().getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
