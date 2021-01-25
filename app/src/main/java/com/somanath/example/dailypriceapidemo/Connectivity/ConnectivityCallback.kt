package com.somanath.example.dailypriceapidemo.Connectivity

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET

class ConnectivityCallback(private val listener: IConnectivityChangedListener) : ConnectivityManager
.NetworkCallback() {
    override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
        val connected = capabilities.hasCapability(NET_CAPABILITY_INTERNET)
        listener.onStateChanged(connected)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        listener.onStateChanged(true)
    }
    override fun onLost(network: Network) {
        listener.onStateChanged(false)
    }
}