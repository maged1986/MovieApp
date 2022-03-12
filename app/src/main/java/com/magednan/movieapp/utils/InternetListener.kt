package com.magednan.movieapp.utils

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

open class InternetListener : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        connectivityReceiverListener?.onNetworkConnectionChanger(isConnectedOrConnecting(context!!))

    }

    @SuppressLint("ServiceCast")
    private fun isConnectedOrConnecting(context: Context):Boolean{
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    interface ConnectivityReceiverListener{
        fun onNetworkConnectionChanger(isConnected: Boolean)
    }

    companion object{
        var connectivityReceiverListener:ConnectivityReceiverListener? = null
    }
}