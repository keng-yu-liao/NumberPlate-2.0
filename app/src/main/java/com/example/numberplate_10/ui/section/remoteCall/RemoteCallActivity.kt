package com.example.numberplate_10.ui.section.remoteCall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import com.example.numberplate_10.R
import com.example.numberplate_10.common.ApiConfig.API.CALL_SOCKET_NAME
import com.example.numberplate_10.common.ApiConfig.API.CALL_SOCKET_PORT
import com.example.numberplate_10.core.connection.ConnectionManager
import com.example.numberplate_10.data.httpObj.SocketConnectRq
import com.example.numberplate_10.data.httpObj.SocketConnectRs
import retrofit2.Callback
import retrofit2.Response

class RemoteCallActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_call)
    }

    fun callSocketInit() {
        //開啟Socke
        sendCallSocketConnect()

    }

    fun sendCallSocketConnect() {
        val socketConnectRq = SocketConnectRq(CALL_SOCKET_NAME, CALL_SOCKET_PORT)
        ConnectionManager.getInstance().socketConnect(socketConnectRq.socketName, socketConnectRq.portNum).enqueue(object : Callback<SocketConnectRs>{
            override fun onFailure(call: retrofit2.Call<SocketConnectRs>, t: Throwable) {

            }

            override fun onResponse(call: retrofit2.Call<SocketConnectRs>, response: Response<SocketConnectRs>) {

            }

        })

    }
}
