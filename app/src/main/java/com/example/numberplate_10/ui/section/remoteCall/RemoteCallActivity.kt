package com.example.numberplate_10.ui.section.remoteCall

import android.os.Bundle
import android.text.TextUtils
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.numberplate_10.R
import com.example.numberplate_10.common.ApiConfig.API.STORE_TABLE
import com.example.numberplate_10.common.ConnectionCode.STATUS_REMOTE_CALLED
import com.example.numberplate_10.common.TransDataCode.ACCOUNT_NAME
import com.example.numberplate_10.core.connection.ConnectionListener
import com.example.numberplate_10.core.connection.ConnectionManager
import com.example.numberplate_10.data.httpObj.*
import com.example.numberplate_10.ui.base.BaseActivity
import com.example.numberplate_10.utils.DialogUtil
import kotlinx.android.synthetic.main.activity_remote_call.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RemoteCallActivity : BaseActivity(), RemoteCallAdapter.OnItemListener {
    lateinit var strAccountName: String
    private var remoteCallManager = RemoteCallManager()
    private var clickLock: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_call)
        getExtra()
        init()

    }

    override fun onDestroy() {
        super.onDestroy()
        remoteCallManager.stopRemoteCallJob()

    }

    override fun onClick(num: String) {
        if (!clickLock) {
            clickLock = true
            showLoading()
            sendUpdateWaitNum(STORE_TABLE, num, getNumIndex(num))

        }
    }

    private fun getExtra() {
        strAccountName = intent.getStringExtra(ACCOUNT_NAME) ?: ""

    }

    private fun init() {
        showLoading()
        initRcv()
        sendUpdateStartStatus(strAccountName, STATUS_REMOTE_CALLED)

    }

    private fun initRcv() {
        rcv_remote_call?.apply {
            layoutManager = LinearLayoutManager(this@RemoteCallActivity, RecyclerView.VERTICAL, false)
            adapter = RemoteCallAdapter(this@RemoteCallActivity, remoteCallManager.getRemoteCallList())

        }
    }

    private fun updateRcv(remoteCallNumList: ArrayList<String>?) {
        if (remoteCallNumList == null) {
            remoteCallManager.getRemoteCallList().clear()

        } else {
            remoteCallManager.setRemoteCallList(remoteCallNumList)

        }
        rcv_remote_call.adapter?.notifyDataSetChanged()

    }

    private fun getAllNum() {
        val remoteJob = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                sendAllWaitNum(STORE_TABLE)
                delay(2000)
            }
        }

        remoteCallManager.setRemoteCallJob(remoteJob)
    }

    private fun processWaitNum(oriWaitNum: String): ArrayList<String> {
        val waitNumArray = oriWaitNum.split("*")
        val round = waitNumArray.size / 5
        val roundRest = waitNumArray.size % 5
        val processNumList = ArrayList<String>()

        for (i in 0..round) {
            // 整除5，滿排數字
            if (i < round) {
                processNumList.add("${waitNumArray[i*5]}*${waitNumArray[i*5+1]}*${waitNumArray[i*5+2]}*${waitNumArray[i*5+3]}*${waitNumArray[i*5+4]}")

                // 剩下的排數，未滿
            } else {
                var lastWaitNumLine: String = ""

                for (k in 0 until roundRest) {
                    lastWaitNumLine = if (k == 0) {
                        waitNumArray[round*5 + k]

                    } else {
                        "$lastWaitNumLine*${waitNumArray[round*5 + k]}"

                    }

                }

                //有缺補零
                for (j in 0 until (5 - roundRest)) {
                    lastWaitNumLine = "$lastWaitNumLine*0"

                }

                processNumList.add(lastWaitNumLine)
            }
        }

        return processNumList
    }

    private fun getNumIndex(updateNum: String) : String {
        return updateNum.toInt().let {
            val tmp = it + 10
            tmp.toString().toCharArray()[0].toString()
        }

    }

    private fun sendUpdateStartStatus(accountName: String, updateStatus: String) {
        val updateStartingStatusRq = UpdateStartingStatusRq(accountName, updateStatus)
        ConnectionManager.sendUpdateStartingStatus(updateStartingStatusRq, object : ConnectionListener<String> {
            override fun onFail(msg: String) {
                cancelLoading()
                DialogUtil.showDialog(this@RemoteCallActivity, getString(R.string.remote_call_update_status_fail))
            }

            override fun onSuccess(data: String) {
                cancelLoading()
                DialogUtil.showDialog(this@RemoteCallActivity, getString(R.string.remote_call_update_status_success))
                getAllNum()

            }
        })
    }

    private fun sendAllWaitNum(tableName: String) {
        val getAllWaitNumRq = GetAllWaitNumRq(tableName)
        ConnectionManager.sendGetAllWaitNum(getAllWaitNumRq, object : ConnectionListener<String> {
            override fun onFail(msg: String) {
                cancelLoading()
                DialogUtil.showDialog(this@RemoteCallActivity, getString(R.string.remote_call_get_all_num_fail))

            }

            override fun onSuccess(data: String) {
                cancelLoading()
                if (!TextUtils.isEmpty(data)) {
                    updateRcv(processWaitNum(data))
                    tv_remote_call_no_num.visibility = INVISIBLE

                } else {
                    updateRcv(null)
                    tv_remote_call_no_num.visibility = VISIBLE

                }
            }
        })
    }

    private fun sendUpdateWaitNum(tableName: String, updateNum: String, numIndex: String) {
        val updateWaitNumRq = UpdateWaitNumRq(tableName, updateNum, numIndex)
        ConnectionManager.sendUpdateWaitNum(updateWaitNumRq, object : ConnectionListener<String> {
            override fun onFail(msg: String) {
                clickLock = false
                DialogUtil.showDialog(this@RemoteCallActivity, getString(R.string.remote_call_update_wait_num_fail))

            }

            override fun onSuccess(data: String) {
                clickLock = false
            }

        })

    }

}
