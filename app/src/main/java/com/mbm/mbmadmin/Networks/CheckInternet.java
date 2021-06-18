package com.mbm.mbmadmin.Networks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

public class CheckInternet {

    @NonNull
    public static Boolean isConnected(@NonNull Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting())
        {
            return networkInfo != null && networkInfo.isConnected();
        }else
        {
            return false;
        }
    }
}
