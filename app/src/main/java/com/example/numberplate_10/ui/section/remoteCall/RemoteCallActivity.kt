package com.example.numberplate_10.ui.section.remoteCall

import android.os.Bundle
import android.util.Log
import com.example.numberplate_10.R
import com.example.numberplate_10.common.ApiConfig.API.CALL_SOCKET_NAME
import com.example.numberplate_10.common.ApiConfig.API.CALL_SOCKET_PORT
import com.example.numberplate_10.common.ApiConfig.LOG_TAG
import com.example.numberplate_10.common.ConnectionCode.SOCKET_CONNECTED
import com.example.numberplate_10.common.ConnectionCode.STATUS_SUCCESS
import com.example.numberplate_10.core.connection.ConnectionManager
import com.example.numberplate_10.core.socket.SocketManager
import com.example.numberplate_10.core.socket.SocketManager.SocketConnectListener
import com.example.numberplate_10.data.httpObj.SocketConnectRq
import com.example.numberplate_10.data.httpObj.SocketConnectRs
import com.example.numberplate_10.ui.base.BaseActivity
import com.example.numberplate_10.utils.DialogUtil
import com.example.numberplate_10.utils.ToastUtil
import retrofit2.Callback
import retrofit2.Response

class RemoteCallActivity : BaseActivity() {
    var isEstablishCallSocket = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_call)

        init()
    }

    fun init() {
        showLoading()
        callSocketInit()

    }

    fun callSocketInit() {
        //發送指令開啟Socket監聽
        sendCallSocketConnect()

        //停滯2秒開啟Socket監聽
        Thread.sleep(1500)

        //Socket 連接
        SocketManager.establishCallSocketConnection(CALL_SOCKET_NAME, CALL_SOCKET_PORT, object : SocketConnectListener {
            override fun onSocketConnect(connectCode: String, socketInfo: String) {
                Log.d(LOG_TAG, "Socket connect status : $connectCode")
                Log.d(LOG_TAG, "Socket info : $socketInfo")
                if (SOCKET_CONNECTED.equals(connectCode)) {
                    isEstablishCallSocket = true

                } else {
                    cancelLoading()
                    DialogUtil.showDialog(this@RemoteCallActivity, getString(R.string.remote_call_socket_establish_fail))

                }
            }
        })
    }

    fun sendCallSocketConnect() {
        val socketConnectRq = SocketConnectRq(CALL_SOCKET_NAME, CALL_SOCKET_PORT)
        ConnectionManager.getInstance().socketConnect(socketConnectRq.socketName, socketConnectRq.portNum).enqueue(object : Callback<SocketConnectRs>{
            override fun onFailure(call: retrofit2.Call<SocketConnectRs>, t: Throwable) {
                Log.d(LOG_TAG, t.message!!)

                cancelLoading()
                DialogUtil.showDialog(this@RemoteCallActivity, getString(R.string.remote_call_socket_connect_fail))
            }

            override fun onResponse(call: retrofit2.Call<SocketConnectRs>, response: Response<SocketConnectRs>) {
                response.body()?.run {
                    Log.d(LOG_TAG, "SocketAPI status : " + this.status)

                    cancelLoading()
                    if (STATUS_SUCCESS.equals(this.status)) {
                        ToastUtil.showToast(this@RemoteCallActivity, getString(R.string.remote_call_connect_success))

                    } else {
                        DialogUtil.showDialog(this@RemoteCallActivity, getString(R.string.remote_call_socket_connect_fail))

                    }
                }
            }

        })

    }
}
