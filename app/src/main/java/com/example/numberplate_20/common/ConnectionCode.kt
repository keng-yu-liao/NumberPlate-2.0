package com.example.numberplate_20.common

object ConnectionCode {
    const val STATUS_SUCCESS = "0000"
    const val STATUS_FAIL = "9999"

    //初始狀態碼
    const val STATUS_REMOTE_UNCALLED = "0001"
    const val STATUS_REMOTE_CALLED = "0002"

    //Socket 連接狀態碼
    const val SOCKET_CONNECTED = "S0000"
    const val SOCKET_CONNECT_FAIL = "S9999"
}