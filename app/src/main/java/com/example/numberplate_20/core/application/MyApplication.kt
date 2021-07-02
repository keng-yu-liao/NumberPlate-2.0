package com.example.numberplate_20.core.application

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    companion object {
        lateinit var mContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }
}