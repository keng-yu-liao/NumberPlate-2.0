package com.example.numberplate_10.common

object ApiConfig {
    const val LOG_TAG = "LIAO"

    object API {

        //APP 設定參數
        const val BASE_URL: String = "http://35.229.169.52/NumberPlate-1.0-Server/"
        const val STORE_TABLE: String = "store_test_num_status"
        const val TIMEOUT_CONNECT = 5L
        const val TIMEOUT_READ = 5L

        const val SOCKET_IP = "35.229.169.52"

        const val CALL_SOCKET_NAME = "test_call_socket"
        const val CALL_SOCKET_PORT = "1500"
        const val PAD_SOCKET_NAME = "test_pad_socket"
        const val PAD_SOCKET_PORT = "1501"

        //API名稱
        const val LOGIN: String = "Login.php"
        const val GET_STARTING_STATUS: String = "GetStartingStatus.php"
        const val CALL_SOCKET_CONNECT: String = "CallSocketConnect.php"
    }
}