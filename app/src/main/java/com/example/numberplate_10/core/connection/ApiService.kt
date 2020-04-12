package com.example.numberplate_10.core.connection

import com.example.numberplate_10.common.ApiConfig
import com.example.numberplate_10.data.httpObj.LoginRs
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {
    @GET(ApiConfig.API.LOGIN)
    fun login(
            @Query("accountName") accountName: String,
            @Query("accountPassword") accountPassword: String
    ): Call<LoginRs>

}