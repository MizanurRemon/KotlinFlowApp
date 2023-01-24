package com.example.kotlinflowapp.Helpers

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
fun Context.checkConnect(): kotlinx.coroutines.flow.Flow<Boolean> = callbackFlow {
    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            this@callbackFlow.trySend(true).isSuccess
        }

        override fun onLost(network: Network) {
            super.onLost(network)

            this@callbackFlow.trySend(false).isSuccess
        }
    }

    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    //register network callback
    manager.registerNetworkCallback(
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build(), callback
    )

    //prevent memory leak by unregistering network callback
    awaitClose {
        manager.unregisterNetworkCallback(callback)
    }
}