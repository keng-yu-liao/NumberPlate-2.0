package com.example.numberplate_10.data.httpObj

data class UpdateWaitNumRq (
    val storeTableName: String,
    val updateNum: String,
    val numIndex: String

)