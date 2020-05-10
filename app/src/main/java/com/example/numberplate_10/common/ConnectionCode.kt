package com.example.numberplate_10.common

object ConnectionCode {
    const val STATUS_SUCCESS = "0000"
    const val STATUS_FAIL = "9999"

    //初始狀態碼
    const val STATUS_UNINITIAL = "0001"
    const val STATUS_INITED = "0002"

    //Socket 連接狀態碼
    const val SOCKET_CONNECTED = "S0000"
    const val SOCKET_CONNECT_FAIL = "S9999"
}