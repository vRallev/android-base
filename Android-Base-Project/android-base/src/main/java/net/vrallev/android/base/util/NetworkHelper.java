package net.vrallev.android.base.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Ralf Wondratschek
 */
@SuppressWarnings("UnusedDeclaration")
public class NetworkHelper {

    private final ConnectivityManager mConnectivityManager;

    public NetworkHelper(Context context) {
        this((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
    }

    public NetworkHelper(ConnectivityManager connectivityManager) {
        mConnectivityManager = connectivityManager;
    }

    public boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
