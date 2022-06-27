package com.example.numberplate_20.ui.section.operation

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import com.example.numberplate_20.R
import com.example.numberplate_20.core.websocket.LiaoWebSocketClient
import com.example.numberplate_20.core.websocket.LiaoWebSocketClientService
import com.example.numberplate_20.ui.base.BaseActivity

class OperationActivity : BaseActivity() {
    // webSocket
    lateinit var liaoWebSocketClientService: LiaoWebSocketClientService
    lateinit var binder: Binder
    private var liaoWebSocketClient: LiaoWebSocketClient? = null
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            binder = p1 as LiaoWebSocketClientService.LiaoWebSocketBinder
            liaoWebSocketClientService = (binder as LiaoWebSocketClientService.LiaoWebSocketBinder).service
            liaoWebSocketClient = liaoWebSocketClientService.client
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            TODO("Not yet implemented")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation)
    }
}