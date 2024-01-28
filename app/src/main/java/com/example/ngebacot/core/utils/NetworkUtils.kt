package com.example.ngebacot.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun checkInternetConnection(context: Context): Boolean {
        // Implementasi untuk mengecek koneksi internet
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Periksa koneksi jaringan aktif
        val network = connectivityManager.activeNetwork ?: return false

        // Periksa kemampuan jaringan dari koneksi yang aktif
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            // Periksa apakah perangkat terhubung melalui Wi-Fi atau seluler
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
}