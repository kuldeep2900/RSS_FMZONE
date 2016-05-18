package adapter;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by ayush on 12/5/16.
 */
public class Constants {
    public static boolean checkNetworkStatus(final Context context) {

        boolean networkStatus = false;


        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // check for wifi
        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        // check for mobile data
        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() && wifi.isConnected()) {
            networkStatus = true;
        } else if (mobile.isAvailable() && mobile.isConnected()) {
            networkStatus = true;
        } else {
            networkStatus = false;
        }

        return networkStatus;

    }
}
