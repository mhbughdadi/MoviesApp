package com.example.mohamed.movieapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Mohamed on 11/24/2016.
 */

public class Utils {
    public static boolean isConnectionOpen(Context context)
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo()!=null;
    }
}
