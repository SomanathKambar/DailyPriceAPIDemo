package com.somanath.example.dailypriceapidemo

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.somanath.example.dailypriceapidemo.Connectivity.ConnectivityCallback
import com.somanath.example.dailypriceapidemo.Connectivity.IConnectivityChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IConnectivityChangedListener {

    @RequiresApi(Build.VERSION_CODES.N)
    private val networkChangeReceiver = ConnectivityCallback(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            addNetWorkChangeListener()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun addNetWorkChangeListener() {
        networkChangeReceiver.isConnectedToNetWork.observe(this, Observer {
            onStateChanged(it)
        })
    }

    override fun onStateChanged(isConnected: Boolean) {
        networkError.visibility = if (isConnected) View.GONE else View.VISIBLE
    }

}