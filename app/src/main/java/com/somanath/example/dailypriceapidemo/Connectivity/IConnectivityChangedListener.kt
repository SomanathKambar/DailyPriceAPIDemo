package com.somanath.example.dailypriceapidemo.Connectivity

interface IConnectivityChangedListener {
    fun onStateChanged(isConnected: Boolean)
}