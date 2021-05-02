package com.example.numberplate_20.core.websocket

import com.example.numberplate_20.common.ApiConfig
import com.example.numberplate_20.common.ApiConfig.API.TIMEOUT_READ
import com.example.numberplate_20.common.ApiConfig.API.TIMEOUT_WRITE
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.util.concurrent.TimeUnit

class WebsocketRepository : WebSocketListener() {

    private lateinit var okHttpClient: OkHttpClient
    private lateinit var socket: WebSocket
    private lateinit var mWebSocketListener: com.example.numberplate_20.core.websocket.WebSocketListener

    fun initClient(webSocketListener: com.example.numberplate_20.core.websocket.WebSocketListener) {
        okHttpClient = OkHttpClient.Builder()
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
                .build()

        val request = Request.Builder().url(ApiConfig.WEBSOCKET.WEBSOCKET_URL).build()
        socket = okHttpClient.newWebSocket(request, this@WebsocketRepository)
        mWebSocketListener = webSocketListener
        okHttpClient.dispatcher.executorService.shutdown()
    }

    fun send(text: String) {
        socket.send(text)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        mWebSocketListener.onClosed(reason)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        mWebSocketListener.onClosing(reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        mWebSocketListener.onFailure(t.message!!)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        mWebSocketListener.onMessage(text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        mWebSocketListener.onOpen(response.message)
    }
}