package com.enesgemci.loginvuz.core.extension

import android.content.Context
import android.net.ConnectivityManager

val Context.isConnectedToNet: Boolean
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var connected = false
        val networkInfoList = connectivityManager.allNetworks

        networkInfoList.forEach { network ->
            val networkInfo = connectivityManager.getNetworkInfo(network)

            networkInfo?.let {
                connected = it.isConnected

                if (connected) {
                    return true
                }
            }
        }

        return connected
    }