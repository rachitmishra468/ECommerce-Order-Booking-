package com.uk.restaurant.GreenVich.utill;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Developer on 02-06-2016.
 */
public class UtillClass {

    Context context;

    public UtillClass(Context context) {
        this.context = context;
    }

    public boolean isInternetConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected;
        return isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
