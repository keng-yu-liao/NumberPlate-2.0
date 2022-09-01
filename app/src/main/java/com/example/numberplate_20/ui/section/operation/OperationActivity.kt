package com.example.numberplate_20.ui.section.operation

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import com.example.numberplate_20.R
import com.example.numberplate_20.core.websocket.LiaoWebSocketClient
import com.example.numberplate_20.core.websocket.LiaoWebSocketClientService
import com.example.numberplate_20.data.httpObj.REQUEST_WAIT_NUM
import com.example.numberplate_20.data.httpObj.WsActionObj
import com.example.numberplate_20.data.httpObj.WsRequest
import com.example.numberplate_20.ui.base.BaseActivity
import com.google.gson.Gson

class OperationActivity : BaseActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private val UPDATE_RATE = 2 * 1000L
    private var fileName = ""
    private val gson = Gson()

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

        fileName = intent.getStringExtra("fileName") ?: ""
        startWebSocket()
    }

    private fun startWebSocket() {
        val intent = Intent(this, LiaoWebSocketClientService::class.java)
        startService(intent)

        val bindIntent = Intent(this, LiaoWebSocketClientService::class.java)
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE)

        updateWaitNum()
    }

    private fun updateWaitNum() {
        handler.postDelayed(updateNumRunnable, UPDATE_RATE)
    }

    private val updateNumRunnable = object : Runnable {
        override fun run() {
            val wsRequest = WsRequest(
                actionCode = REQUEST_WAIT_NUM,
                actionObj = WsActionObj.WaitNumRequest(
                        fileName = "$fileName.txt"
                )
            )
            liaoWebSocketClientService.sendMsg(gson.toJson(wsRequest))

            handler.postDelayed(this, UPDATE_RATE)
        }
    }
}