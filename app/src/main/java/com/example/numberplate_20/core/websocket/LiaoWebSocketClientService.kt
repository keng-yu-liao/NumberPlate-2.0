package com.example.numberplate_20.core.websocket

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.example.numberplate_20.common.ApiConfig.API.WS_URL
import java.net.URI

class LiaoWebSocketClientService : Service() {
    var client: LiaoWebSocketClient? = null
    private val binder = LiaoWebSocketBinder()
    private val wsUrl = WS_URL

    inner class LiaoWebSocketBinder : Binder() {
        val service: LiaoWebSocketClientService
            get() = this@LiaoWebSocketClientService
    }

    override fun onBind(p0: Intent?): IBinder {
        Log.d("LIAO", "onBind")
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("LIAO", "onStartCommand")
        initSocketClient()
        handler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d("LIAO", "onDestroy")
        closeConnect()
        super.onDestroy()
    }

    private fun initSocketClient() {
        val uri = URI.create(wsUrl)
        client = LiaoWebSocketClient(uri)
        connect()
    }

    private fun connect() {
        Thread {
            try {
                client?.connectBlocking()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }

    fun closeConnect() {
        try {
            client?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            client = null
        }
    }

    fun sendMsg(msg: String) {
        client?.send(msg)
    }

    // -- websocket heart beat --
    private val HEART_BEAT_RATE = 10 * 1000L
    private val handler = Handler(Looper.getMainLooper())
    private val heartBeatRunnable = object : Runnable {
        override fun run() {
            Log.d("LIAO", "心跳包检测websocket连接状态")
            if (client != null) {
                if (client!!.isClosed) {
                    reconnectWs()
                }
            } else {
                //如果client已为空，重新初始化连接
                initSocketClient()
            }
            //每隔一定的时间，对长连接进行一次心跳检测
            handler.postDelayed(this, HEART_BEAT_RATE)
        }
    }

    private fun reconnectWs() {
        Thread {
            try {
                Log.d("LIAO", "开启重连")
                client?.reconnectBlocking()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()
    }
}