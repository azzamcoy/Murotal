package com.example.murotal.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MethodJava {
    private Context context;

    public MethodJava(Context context) {
        this.context = context;
    }

    public boolean checkInternet(){
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = con.getActiveNetworkInfo();

        return info != null && info.isConnected();
    }
}
