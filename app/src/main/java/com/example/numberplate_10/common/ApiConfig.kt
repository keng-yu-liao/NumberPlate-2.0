package com.example.numberplate_10.common

object ApiConfig {
    const val LOG_TAG = "LIAO"

    object API {
        const val BASE_URL: String = "http://35.229.169.52/NumberPlate-1.0-Server/"
        const val STORE_TABLE: String = "store_test_num_status"
        const val TIMEOUT_CONNECT = 5L
        const val TIMEOUT_READ = 5L
        const val SOCKET_PORT = ""

        const val LOGIN: String = "Login.php"
        const val GET_STARTING_STATUS:  String = "GetStartingStatus.php"
    }
}