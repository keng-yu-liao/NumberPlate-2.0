package com.example.numberplate_10.ui.section.remoteCall

import android.os.Bundle
import android.util.Log
import com.example.numberplate_10.R
import com.example.numberplate_10.common.ApiConfig.API.STORE_TABLE
import com.example.numberplate_10.common.ConnectionCode.STATUS_REMOTE_CALLED
import com.example.numberplate_10.common.TransDataCode.ACCOUNT_NAME
import com.example.numberplate_10.core.connection.ConnectionListener
import com.example.numberplate_10.core.connection.ConnectionManager
import com.example.numberplate_10.data.httpObj.*
import com.example.numberplate_10.ui.base.BaseActivity
import com.example.numberplate_10.utils.DialogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RemoteCallActivity : BaseActivity() {
    lateinit var strAccountName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_call)
        getExtra()
        init()

    }

    private fun getExtra() {
        strAccountName = intent.getStringExtra(ACCOUNT_NAME) ?: ""

    }

    private fun init() {
        showLoading()
        sendUpdateStartStatus(strAccountName, STATUS_REMOTE_CALLED)

    }

    private fun getAllNum() {
        GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                sendAllWaitNum(STORE_TABLE)
                delay(2000)
            }
        }
    }

    private fun sendUpdateStartStatus(accountName: String, updateStatus: String) {
        val updateStartingStatusRq = UpdateStartingStatusRq(accountName, updateStatus)
        ConnectionManager.sendUpdateStartingStatus(updateStartingStatusRq, object : ConnectionListener<String>{
            override fun onFail(msg: String) {
                cancelLoading()
                DialogUtil.showDialog(this@RemoteCallActivity, getString(R.string.remote_call_update_status_fail))
            }

            override fun onSuccess(t: String) {
                cancelLoading()
                DialogUtil.showDialog(this@RemoteCallActivity, getString(R.string.remote_call_update_status_success))
                getAllNum()

            }

        })
    }

    private fun sendAllWaitNum(accountName: String) {
        val getAllWaitNumRq = GetAllWaitNumRq(accountName)
        ConnectionManager.sendGetAllWaitNum(getAllWaitNumRq, object : ConnectionListener<String> {
            override fun onFail(msg: String) {
                DialogUtil.showDialog(this@RemoteCallActivity, getString(R.string.remote_call_get_all_num_fail))

            }

            override fun onSuccess(t: String) {
                Log.d("liao", t.toString())

            }
        })

    }

}
