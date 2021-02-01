package com.example.numberplate_20.data.customException

import com.example.numberplate_20.R
import com.example.numberplate_20.core.application.MyApplication
import java.io.IOException

class NoInternetException() : IOException() {
    override val message: String
        get() = MyApplication.mContext.getString(R.string.dialog_error_no_internet)

}