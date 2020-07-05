package com.example.numberplate_10.core.connection

import com.example.numberplate_10.common.ApiConfig
import com.example.numberplate_10.common.ConnectionCode.STATUS_REMOTE_CALLED
import com.example.numberplate_10.common.ConnectionCode.STATUS_SUCCESS
import com.example.numberplate_10.data.httpObj.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
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
                    .addInterceptor(createLoggingInterceptor())
                    .build()
        }

        private fun createLoggingInterceptor(): HttpLoggingInterceptor {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return httpLoggingInterceptor
        }

        fun sendInit(initRq: InitRq, connectionListener: ConnectionListener<InitRs>) {
            getInstance().init(initRq.tableName, initRq.accountName).enqueue(object : Callback<Response> {
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    t.message?.let {
                        connectionListener.onFail(it)

                    }
                }

                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    response.body()?.run {
                        if (STATUS_SUCCESS == (this.status)) {
                            val initRs = InitRs(this.data)
                            connectionListener.onSuccess(initRs)

                        } else {
                            connectionListener.onFail(this.data)

                        }
                    }
                }
            })
        }

        fun sendUpdateStartingStatus(updateStartingStatusRq: UpdateStartingStatusRq, connectionListener: ConnectionListener<String>) {
            getInstance().updateStartingStatus(updateStartingStatusRq.accountName, updateStartingStatusRq.updateStatus).enqueue(object : Callback<Response> {
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    t.message?.let {
                        connectionListener.onFail(it)

                    }
                }

                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    response.body()?.run {
                        if (STATUS_SUCCESS == (this.status)) {
                            connectionListener.onSuccess("")

                        } else {
                            connectionListener.onFail("")

                        }
                    }
                }
            })
        }

        fun sendGetStartingStatus(getStartingStatusRq: GetStartingStatusRq, connectionListener: ConnectionListener<String>) {
            getInstance().getStartingStatus(getStartingStatusRq.accountName).enqueue(object : Callback<Response> {
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    t.message?.let {
                        connectionListener.onFail(it)

                    }
                }

                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    response.body()?.run {
                        if (STATUS_SUCCESS == (this.status)) {
                            if(STATUS_REMOTE_CALLED == this.data) {
                                connectionListener.onSuccess("")
                            }

                            connectionListener.onFail("")

                        } else {
                            connectionListener.onFail(this.status)

                        }
                    }
                }
            })
        }

        fun sendGetAllWaitNum(getAllWaitNumRq: GetAllWaitNumRq, connectionListener: ConnectionListener<String>) {
            getInstance().getAllWaitNum(getAllWaitNumRq.storeTableName).enqueue(object : Callback<Response> {
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    t.message?.let {
                        connectionListener.onFail(it)

                    }
                }

                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    response.body()?.run {
                        if (STATUS_SUCCESS == (this.status)) {
                            connectionListener.onSuccess(this.data)

                        } else {
                            connectionListener.onFail(this.status)

                        }
                    }
                }
            })
        }
    }
}