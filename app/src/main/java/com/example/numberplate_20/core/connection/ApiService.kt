package com.example.numberplate_20.core.connection

import com.example.numberplate_20.common.ApiConfig
import com.example.numberplate_20.data.httpObj.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiConfig.API.CREATE_FILE)
    fun createFile(
            @Query("fileName") fileName: String
    ): Call<Response>

    @GET(ApiConfig.API.REQUEST_UNCALL_NUM)
    fun requestUncallNum(
            @Query("fileName") fileName: String
    ): Call<Response>

    @GET(ApiConfig.API.CALL_NUM)
    fun callNum(
            @Query("fileName") fileName: String,
            @Query("callNum") callNum: String
    ): Call<Response>
}