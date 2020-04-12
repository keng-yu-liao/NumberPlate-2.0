package com.example.numberplate_10.core.connection

import com.example.numberplate_10.common.ApiConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ConnectionManager {

    companion object {
        private var apiService: ApiService? = null

        @Synchronized
        fun getInstance(): ApiService {
            return apiService ?: build()
        }

        @Synchronized
        private fun build(): ApiService {
            return Retrofit.Builder()
                    .client(createOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ApiConfig.API.BASE_URL)
                    .build().create(ApiService::class.java)
        }

        private fun createOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .connectTimeout(ApiConfig.API.TIMEOUT_CONNECT, TimeUnit.SECONDS)
                    .readTimeout(ApiConfig.API.TIMEOUT_READ, TimeUnit.SECONDS)
                    .build()
        }
    }
}