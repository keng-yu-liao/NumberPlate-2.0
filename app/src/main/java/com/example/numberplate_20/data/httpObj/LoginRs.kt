package com.example.numberplate_20.data.httpObj

data class LoginRs (
        val status: String,
        val data: String
)

data class LoginRsData (
    val storeName: String,
    val storeTableName: String
)