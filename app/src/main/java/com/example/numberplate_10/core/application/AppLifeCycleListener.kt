package com.example.numberplate_10.core.application

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.numberplate_10.common.ApiConfig.LOG_TAG

class AppLifeCycleListener : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun aaa() {
        Log.d(LOG_TAG, "aaa")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun bbb() {
        Log.d(LOG_TAG, "bbb")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun ccc() {
        Log.d(LOG_TAG, "ccc")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun closeAPP() {
        Log.d(LOG_TAG, "close app")
    }
}