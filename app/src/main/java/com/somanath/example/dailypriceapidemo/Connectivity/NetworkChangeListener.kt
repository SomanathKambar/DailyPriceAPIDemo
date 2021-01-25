package com.somanath.example.dailypriceapidemo.Connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

import android.net.NetworkInfo
import java.lang.Boolean


class NetworkChangeListener: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val manager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo

        if (networkInfo != null && networkInfo.state == NetworkInfo.State.CONNECTED) {
        } else if (intent!!.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {    //Boolean that indicates whether there is a complete lack of connectivity
        }

    }
}