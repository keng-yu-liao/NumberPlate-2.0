package com.example.numberplate_20.core.websocket

import android.util.Log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

open class LiaoWebSocketClient(serverUri: URI) : WebSocketClient(serverUri) {
    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d("Liao Socket", "onOpen()")
    }

    override fun onMessage(message: String?) {
        Log.d("Liao Socket", "onMessage: $message")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d("Liao Socket", "onClose: $reason")
    }

    override fun onError(ex: Exception?) {
        Log.d("Liao Socket", "onError()")
    }
}