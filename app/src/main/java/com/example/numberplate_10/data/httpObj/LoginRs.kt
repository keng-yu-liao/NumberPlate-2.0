package com.example.numberplate_10.data.httpObj

data class LoginRs (
        val status: String,
        val data: String
)

data class LoginRsData (
    val storeName: String,
    val storeTableName: String
)