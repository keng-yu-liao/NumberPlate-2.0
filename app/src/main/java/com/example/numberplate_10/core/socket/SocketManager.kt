package com.example.numberplate_10.core.socket

import com.example.numberplate_10.common.ApiConfig
import com.example.numberplate_10.common.ApiConfig.API.CALL_SOCKET_NAME
import com.example.numberplate_10.common.ApiConfig.API.PAD_SOCKET_NAME
import com.example.numberplate_10.common.ConnectionCode.SOCKET_CONNECTED
import com.example.numberplate_10.common.ConnectionCode.SOCKET_CONNECT_FAIL
import java.io.IOException
import java.net.Socket

class SocketManager {
    interface SocketConnectListener {
        fun onSocketConnect(connectCode: String, socketInfo: String)
    }

    companion object {
        lateinit var callSocket:Socket
        lateinit var padSocket:Socket

        fun establishCallSocketConnection(socketName: String, portNum: String, socketConnectListener: SocketConnectListener) {
            Thread {
                try {
                    val tmpSocket = Socket(ApiConfig.API.SOCKET_IP, Integer.valueOf(portNum))
                    if (tmpSocket.isConnected) {
                        when (socketName) {
                            CALL_SOCKET_NAME -> callSocket = tmpSocket
                            PAD_SOCKET_NAME -> padSocket = tmpSocket

                        }

                        socketConnectListener.onSocketConnect(SOCKET_CONNECTED, tmpSocket.inetAddress.toString())

                    } else {
                        socketConnectListener.onSocketConnect(SOCKET_CONNECT_FAIL, "")

                    }

                } catch (e: IOException) {
                    e.printStackTrace()

                }

            }.start()
        }
    }
}