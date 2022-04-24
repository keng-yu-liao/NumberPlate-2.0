package com.example.numberplate_20.common

object ApiConfig {
    object API {
        //APP 設定參數
        const val MY_EMAIL: String = "g.y.liao81@gmail.com"
        const val TIMEOUT_CONNECT = 5L
        const val TIMEOUT_READ = 5L
        const val TIMEOUT_WRITE = 5L

        //API名稱
        const val LOGIN: String = "Login.php"
        const val CREATE_FILE: String = "createFile.php"
        const val REQUEST_UNCALL_NUM: String = "requestUncallNum.php"
        const val CALL_NUM: String = "callNum.php"
        const val DELETE_FILE: String = "deleteFile.php"
    }
}