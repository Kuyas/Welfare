package com.example.android.welfare;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * We use this class to determine if the application has been connected to either WIFI Or Mobile
 * Network, before we make any network request to the server.
 * The class uses two permission - INTERNET and ACCESS NETWORK STATE, to determine the user's
 * connection stats
 */

public class NetworkStatus {

    static Context context;
    private ConnectivityManager connectivityManager;
    NetworkInfo mobileDataInfo;
    NetworkStatus wifiInfo;

    private boolean isConnected = false;

    private static NetworkStatus networkStatusInstance = new NetworkStatus();


    public static NetworkStatus getInstance(Context ctxt) {
        context = ctxt.getApplicationContext();
        return networkStatusInstance;
    }

    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            isConnected = ((networkInfo != null) && (networkInfo.isAvailable()) && (networkInfo.isConnected()));

            return isConnected;
        } catch (Exception e) {
            Log.e("connectionError", e.toString());
        }

        return isConnected;
    }

}
