package com.example.numberplate_10.core.connection

import com.example.numberplate_10.common.ApiConfig
import com.example.numberplate_10.data.httpObj.GetStartingStatusRs
import com.example.numberplate_10.data.httpObj.LoginRs
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiConfig.API.LOGIN)
    fun login(
            @Query("accountName") accountName: String,
            @Query("accountPassword") accountPassword: String
    ): Call<LoginRs>

    @GET(ApiConfig.API.GET_STARTING_STATUS)
    fun getStartingStatus (
            @Query("accountName") accountName: String
    ): Call<GetStartingStatusRs>

}