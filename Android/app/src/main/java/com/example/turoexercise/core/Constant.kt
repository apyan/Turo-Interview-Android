package com.example.turoexercise.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings

object Constant {

    const val BASE_URL = "https://api.yelp.com/v3/"

    const val API_KEY = "2ROaa2Rh9qu3WVTCms8FoVE4mSfHQHC7QJua95-kKT-PqzIlLSrs4tmHVdtdFw_66-JNfRiJmbCByHTvFNy5dQq-tpfS4FrPpupIzKlgELR3br-r5trpeFhrCRgwWnYx"

    fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let { connectManager ->
            val capabilities = connectManager.getNetworkCapabilities(connectManager.activeNetwork)
            capabilities?.let { capability ->
                if (capability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }

    fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.System.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON,
            0) != 0
    }
}