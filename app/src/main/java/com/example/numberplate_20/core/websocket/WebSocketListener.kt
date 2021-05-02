package com.example.numberplate_20.core.websocket

interface WebSocketListener {
    fun onClosed(msg: String)
    fun onClosing(msg: String)
    fun onFailure(msg: String)
    fun onMessage(msg: String)
    fun onOpen(msg: String)
}