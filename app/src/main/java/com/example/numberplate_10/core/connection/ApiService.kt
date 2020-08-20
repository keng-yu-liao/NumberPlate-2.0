package com.example.numberplate_10.core.connection

import com.example.numberplate_10.common.ApiConfig
import com.example.numberplate_10.data.httpObj.LoginRs
import com.example.numberplate_10.data.httpObj.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiConfig.API.LOGIN)
    fun login(
        @Query("accountName") accountName: String,
        @Query("accountPassword") accountPassword: String
    ): Call<LoginRs>

    @GET(ApiConfig.API.INIT)
    fun init(
        @Query("tableName") tableName: String,
        @Query("accountName") accountName: String
    ): Call<Response>

    @GET(ApiConfig.API.UPDATE_STARTING_STATUS)
    fun updateStartingStatus(
        @Query("accountName") accountName: String,
        @Query("updateStatus") updateStatus: String
    ): Call<Response>

    @GET(ApiConfig.API.GET_STARTING_STATUS)
    fun getStartingStatus (
        @Query("accountName") accountName: String
    ): Call<Response>

    @GET(ApiConfig.API.GET_ALL_WAIT_NUM)
    fun getAllWaitNum (
        @Query("storeTableName") tableName: String
    ): Call<Response>

    @GET(ApiConfig.API.UPDATE_WAIT_NUM)
    fun updateWaitNum (
        @Query("storeTableName") tableName: String,
        @Query("updateNum") updateNum: String,
        @Query("numIndex")numIndex: String
    ): Call<Response>

    @GET(ApiConfig.API.GET_LAST_WAIT_NUM)
    fun getLastWaitNum(
        @Query("storeTableName") tableName: String
    ): Call<Response>

}