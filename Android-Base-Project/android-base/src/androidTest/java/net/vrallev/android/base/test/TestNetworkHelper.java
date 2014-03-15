package net.vrallev.android.base.test;

import android.test.AndroidTestCase;

import net.vrallev.android.base.util.Cat;
import net.vrallev.android.base.util.NetworkHelper;

/**
 * @author Ralf Wondratschek
 */
public class TestNetworkHelper extends AndroidTestCase {

    public void testNetworkHelper() {

        NetworkHelper networkHelper = new NetworkHelper(getContext());
        String wiFiIpAddress = networkHelper.getWiFiIpAddress();
        Cat.d(wiFiIpAddress);

    }

}
