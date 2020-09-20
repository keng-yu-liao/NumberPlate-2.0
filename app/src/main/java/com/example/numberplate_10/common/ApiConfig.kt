package com.example.numberplate_10.common

object ApiConfig {
    const val LOG_TAG = "LIAO"

    object API {

        //APP 設定參數
        const val BASE_API_URL: String = "http://35.229.169.52/NumberPlate-1.0-Server/app/"
        const val BASE_WEB_URL: String = "http://35.229.169.52/NumberPlate-1.0-Web/number_plate_10.html?storeTableName=*STORE_TABLE_NAME&yourNum=*YOUR_NUM"
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
        const val INIT: String = "Init.php"
        const val GET_STARTING_STATUS: String = "GetStartingStatus.php"
        const val UPDATE_STARTING_STATUS: String = "UpdateStartingStatus.php"
        const val GET_ALL_WAIT_NUM: String = "GetAllWaitNum.php"
        const val UPDATE_WAIT_NUM: String = "UpdateWaitNum.php"
        const val GET_LAST_WAIT_NUM: String = "GetLastWaitNum.php"

    }
}