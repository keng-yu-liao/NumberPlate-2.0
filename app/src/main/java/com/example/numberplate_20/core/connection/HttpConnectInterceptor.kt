package com.example.numberplate_20.core.connection

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.numberplate_20.data.customException.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class HttpConnectInterceptor(context: Context) : Interceptor {

    private val mContext = context

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isConnected()) {
            throw NoInternetException()

        } else {
            chain.proceed(chain.request())

        }
    }

    private fun isConnected(): Boolean {
        var result = false
        val connectivityManager: ConnectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false

            }

        } else {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false

                }
            }
        }

        return result
    }

}