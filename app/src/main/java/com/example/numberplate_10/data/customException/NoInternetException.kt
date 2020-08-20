package com.example.numberplate_10.data.customException

import com.example.numberplate_10.R
import com.example.numberplate_10.core.application.MyApplication
import java.io.IOException

class NoInternetException() : IOException() {
    override val message: String
        get() = MyApplication.mContext.getString(R.string.error_no_internet)

}