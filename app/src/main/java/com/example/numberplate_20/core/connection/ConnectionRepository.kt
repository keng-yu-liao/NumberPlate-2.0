package com.example.numberplate_20.core.connection

import com.example.numberplate_20.BuildConfig
import com.example.numberplate_20.common.ApiConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ConnectionRepository {

    private var apiService: ApiService

    init {
        apiService = build()
    }

    private fun build(): ApiService {
        return Retrofit.Builder()
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_API_URL)
                .build().create(ApiService::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(ApiConfig.API.TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .readTimeout(ApiConfig.API.TIMEOUT_READ, TimeUnit.SECONDS)
                .addInterceptor(createLoggingInterceptor())
                .build()
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    fun apiService(): ApiService {
        return  apiService
    }
}