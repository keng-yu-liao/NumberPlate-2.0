package com.example.numberplate_10.core.application

import android.app.Application
import android.content.Context
import com.example.numberplate_10.core.connection.ConnectionManager
import com.example.numberplate_10.core.sharedPreferences.SharedPreferenceProcess

class MyApplication : Application() {
    companion object {
        lateinit var mContext: Context

    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        SharedPreferenceProcess.initSharedPreferences(mContext)
        init()

    }

    private fun init() {
        ConnectionManager.setContext(mContext)

    }
}