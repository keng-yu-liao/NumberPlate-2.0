package com.example.numberplate_20.common

object ApiConfig {
    const val LOG_TAG = "LIAO"

    object API {
        //APP 設定參數
        const val MY_EMAIL: String = "g.y.liao81@gmail.com"
        const val TIMEOUT_CONNECT = 5L
        const val TIMEOUT_READ = 5L
        const val TIMEOUT_WRITE = 5L

        //API名稱
        const val CREATE_FILE: String = "createFile.php"
        const val LOGIN: String = "Login.php"
        const val INIT: String = "Init.php"
        const val GET_STARTING_STATUS: String = "GetStartingStatus.php"
        const val UPDATE_STARTING_STATUS: String = "UpdateStartingStatus.php"
        const val GET_ALL_WAIT_NUM: String = "GetAllWaitNum.php"
        const val UPDATE_WAIT_NUM: String = "UpdateWaitNum.php"
        const val GET_LAST_WAIT_NUM: String = "GetLastWaitNum.php"
    }

    object WEBSOCKET {
        const val WEBSOCKET_URL: String = "ws://123.207.136.134:9010/ajaxchattest"
    }
}