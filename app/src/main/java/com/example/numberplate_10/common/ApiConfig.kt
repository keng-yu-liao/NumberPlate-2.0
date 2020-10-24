package com.example.numberplate_10.common

object ApiConfig {
    const val LOG_TAG = "LIAO"

    object API {

        //APP 設定參數
        const val MY_EMAIL: String = "g.y.liao81@gmail.com"
        const val TIMEOUT_CONNECT = 5L
        const val TIMEOUT_READ = 5L

        //API名稱
        const val LOGIN: String = "Login.php"
        const val INIT: String = "Init.php"
        const val GET_STARTING_STATUS: String = "GetStartingStatus.php"
        const val UPDATE_STARTING_STATUS: String = "UpdateStartingStatus.php"
        const val GET_ALL_WAIT_NUM: String = "GetAllWaitNum.php"
        const val UPDATE_WAIT_NUM: String = "UpdateWaitNum.php"
        const val GET_LAST_WAIT_NUM: String = "GetLastWaitNum.php"

        object APP_CONFIG {
            private var STORE_TABLE: String = ""
            private var FIRST_OPEN: Boolean = true

            fun setStoreTable(tableName: String) {
                STORE_TABLE = tableName
            }

            fun getStoreTable(): String {
                return STORE_TABLE
            }

            fun setFirstOpen() {
                FIRST_OPEN = false
            }

            fun getFirstOpen(): Boolean {
                return FIRST_OPEN
            }
        }
    }
}