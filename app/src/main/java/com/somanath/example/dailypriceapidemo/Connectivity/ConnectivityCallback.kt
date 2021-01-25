package com.somanath.example.dailypriceapidemo.Connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData


@RequiresApi(Build.VERSION_CODES.N)
class ConnectivityCallback(private val context: Context){

    var isConnectedToNetWork =  MutableLiveData<Boolean>()

    init {
        registerNetworkCallback()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun registerNetworkCallback() {
        try {
            val connectivityManager =
               context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val builder = NetworkRequest.Builder()
            connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    isConnectedToNetWork.value = true
                }

                override fun onLost(network: Network) {
                    isConnectedToNetWork.value = false
                }
            }
            )
        } catch (e: Exception) {
            isConnectedToNetWork.value = true
        }
    }
}