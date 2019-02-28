package com.clt.dumas.clem.news.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Return is isOnline if internet connection
 */
public class InternetStatusHelper {
    private static boolean isOnline;
    public static void  init(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        isOnline = networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isOnline(){
        return isOnline;
    }
}
