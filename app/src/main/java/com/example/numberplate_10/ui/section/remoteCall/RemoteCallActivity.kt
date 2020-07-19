package com.example.numberplate_10.ui.section.remoteCall

import android.os.Bundle
import android.text.TextUtils
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_call)
        getExtra()
        init()

    }

    override fun onClick(num: String) {
        sendUpdateWaitNum(STORE_TABLE, num, getNumIndex(num))

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

    private fun updateRcv(remoteCallNumList: ArrayList<RemoteRowData>) {
        remoteCallManager.setRemoteCallList(remoteCallNumList)
        rcv_remote_call.adapter?.notifyDataSetChanged()

    }

    private fun getAllNum() {
        GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                sendAllWaitNum(STORE_TABLE)
                delay(2000)
            }
        }
    }

    private fun processWaitNum(oriWaitNum: String): ArrayList<RemoteRowData> {
        val remoteRowDataList: ArrayList<RemoteRowData> = ArrayList()
        val waitArray = oriWaitNum.split("*").toList()
        val round = waitArray.size / 5
        val roundRest = waitArray.size % 5

        for (i in 0..round) {
            val remoteRowData = RemoteRowData(ArrayList<String>(5))

            if (i < round) {
                for (k in 0..4) {
                    remoteRowData.rowNumList.add(waitArray[i * 5 + k])
                }

                remoteRowDataList.add(remoteRowData)

            }

            if (i == round && roundRest != 0) {
                val roundRestNum = roundRest - 1
                for (k in 0..roundRestNum) {
                    remoteRowData.rowNumList.add(waitArray[i * 5 + k])
                }

                val roundRestNumEmpty: Int = 5 - roundRest
                repeat(roundRestNumEmpty) {
                    remoteRowData.rowNumList.add("0")
                }

                remoteRowDataList.add(remoteRowData)

            }
        }

        return remoteRowDataList
    }

    private fun getNumIndex(updateNum: String) : String {
        return updateNum.toInt().let {
            val tmp = it + 10
            tmp.toString().toCharArray().get(0).toString()
        }

    }

    private fun sendUpdateStartStatus(accountName: String, updateStatus: String) {
        val updateStartingStatusRq = UpdateStartingStatusRq(accountName, updateStatus)
        ConnectionManager.sendUpdateStartingStatus(updateStartingStatusRq, object : ConnectionListener<String> {
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

    private fun sendAllWaitNum(tableName: String) {
        val getAllWaitNumRq = GetAllWaitNumRq(tableName)
        ConnectionManager.sendGetAllWaitNum(getAllWaitNumRq, object : ConnectionListener<String> {
            override fun onFail(msg: String) {
                DialogUtil.showDialog(this@RemoteCallActivity, getString(R.string.remote_call_get_all_num_fail))

            }

            override fun onSuccess(t: String) {
                if (!TextUtils.isEmpty(t)) {
                    updateRcv(processWaitNum(t.toString()))

                } else {
                    updateRcv(ArrayList<RemoteRowData>())

                }
            }
        })
    }

    private fun sendUpdateWaitNum(tableName: String, updateNum: String, numIndex: String) {
        val updateWaitNumRq = UpdateWaitNumRq(tableName, updateNum, numIndex)
        ConnectionManager.sendUpdateWaitNum(updateWaitNumRq, object : ConnectionListener<String> {
            override fun onFail(msg: String) {
                DialogUtil.showDialog(this@RemoteCallActivity, getString(R.string.remote_call_update_wait_num_fail))

            }

            override fun onSuccess(t: String) {

            }

        })

    }

}
