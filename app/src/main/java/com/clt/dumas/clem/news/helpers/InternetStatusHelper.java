package com.clt.dumas.clem.news.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * @author eamosse
 * Dis ce que fait cette classe
 */
public class InternetStatusHelper {
    private static boolean onLine;
    public static void  init(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        onLine = networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isOnLine(){
        return onLine;
    }
}
