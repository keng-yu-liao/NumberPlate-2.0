package com.example.numberplate_10.core.connection

import android.content.Context
import com.example.numberplate_10.R
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
        private lateinit var mContex: Context

        fun setContext(context: Context) {
            mContex = context

        }

        @Synchronized
        fun getInstance(): ApiService {
            return apiService ?: build()
        }

        @Synchronized
        private fun build(): ApiService {
            return Retrofit.Builder()
                    .client(createOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ApiConfig.API.BASE_API_URL)
                    .build().create(ApiService::class.java)
        }

        private fun createOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .connectTimeout(ApiConfig.API.TIMEOUT_CONNECT, TimeUnit.SECONDS)
                    .readTimeout(ApiConfig.API.TIMEOUT_READ, TimeUnit.SECONDS)
                    .addInterceptor(createLoggingInterceptor())
                    .addInterceptor(HttpConnectInterceptor(mContex))
                    .build()
        }

        private fun createLoggingInterceptor(): HttpLoggingInterceptor {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return httpLoggingInterceptor
        }

        fun sendLogin(loginRq: LoginRq, connectionListener: ConnectionListener<String>) {
            getInstance().login(loginRq.accountName, loginRq.accountPassword).enqueue(object : Callback<Response> {
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    t.message?.let {
                        connectionListener.onFail(it)

                    }
                }

                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    response.body()?.run {
                        if (STATUS_SUCCESS == this.status) {
                            connectionListener.onSuccess(this.data)

                        } else {
                            connectionListener.onFail("")

                        }
                    }
                }
            })
        }

        fun sendInit(initRq: InitRq, connectionListener: ConnectionListener<String>) {
            getInstance().init(initRq.tableName, initRq.accountName).enqueue(object : Callback<Response> {
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
                            if (STATUS_REMOTE_CALLED == this.data) {
                                connectionListener.onSuccess("")
                            }

                            connectionListener.onFail(mContex.getString(R.string.choose_remote_first_hint))

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

        fun sendUpdateWaitNum(updateWaitNumRq: UpdateWaitNumRq, connectionListener: ConnectionListener<String>) {
            getInstance().updateWaitNum(updateWaitNumRq.storeTableName, updateWaitNumRq.updateNum, updateWaitNumRq.numIndex).enqueue(object : Callback<Response> {
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    t.message?.let {
                        connectionListener.onFail(it)

                    }
                }

                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    response.body()?.run {
                        if (STATUS_SUCCESS == this.status) {
                            connectionListener.onSuccess(this.data)

                        } else {
                            connectionListener.onFail(this.status)

                        }

                    }
                }
            })

        }

        fun sendGetLastWaitNum(getLastWaitNumRq: GetLastWaitNumRq, connectionListener: ConnectionListener<String>) {
            getInstance().getLastWaitNum(getLastWaitNumRq.storeTableName).enqueue(object : Callback<Response> {
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    t.message?.let {
                        connectionListener.onFail(it)
                    }
                }

                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    response.body()?.run {
                        if (STATUS_SUCCESS == this.status) {
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