package com.example.numberplate_10.ui.section.choose

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.numberplate_10.R
import com.example.numberplate_10.common.ApiConfig
import com.example.numberplate_10.common.TransDataCode.ACCOUNT_NAME
import com.example.numberplate_10.common.TransDataCode.STORE_NAME
import com.example.numberplate_10.core.connection.ConnectionListener
import com.example.numberplate_10.core.connection.ConnectionManager
import com.example.numberplate_10.data.httpObj.GetStartingStatusRq
import com.example.numberplate_10.data.httpObj.InitRq
import com.example.numberplate_10.ui.base.BaseActivity
import com.example.numberplate_10.ui.section.numberPad.NumberPadActivity
import com.example.numberplate_10.ui.section.remoteCall.RemoteCallActivity
import com.example.numberplate_10.utils.DialogUtil
import kotlinx.android.synthetic.main.activity_choose.*

class ChooseActivity : BaseActivity(), View.OnClickListener {
    lateinit var strAccountName: String
    lateinit var strStoreName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        getExtra()
        init()

    }

    private fun getExtra() {
        strAccountName = intent.getStringExtra(ACCOUNT_NAME) ?: ""
        strStoreName = intent.getStringExtra(STORE_NAME) ?: ""

    }

    private fun init() {
        img_choose_remote.setOnClickListener(this)
        tv_choose_remote.setOnClickListener(this)
        img_choose_calling_pad.setOnClickListener(this)
        tv_choose_calling_pad.setOnClickListener(this)

        sendInit(ApiConfig.API.APP_CONFIG.getStoreTable(), strAccountName)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.img_choose_remote, R.id.tv_choose_remote -> {
                gotoRemoteCall()
                finish()

            }

            R.id.img_choose_calling_pad, R.id.tv_choose_calling_pad -> {
                lockBtn()
                sendGetStartingStatus(strAccountName)

            }
        }
    }

    private fun sendInit(tablename: String, accountName: String) {
        showLoading(getString(R.string.choose_init_process))

        val initRq = InitRq(tablename, accountName)
        ConnectionManager.sendInit(initRq, object : ConnectionListener<String> {
            override fun onFail(msg: String) {
                cancelLoading()
                showFailureMsg(msg)

            }

            override fun onSuccess(data: String) {
                cancelLoading()
                DialogUtil.showDialog(this@ChooseActivity, getString(R.string.choose_init_success))

            }
        })
    }

    private fun sendGetStartingStatus(accountName: String) {
        showLoading()

        val getStartingStatusRq = GetStartingStatusRq(accountName)
        ConnectionManager.sendGetStartingStatus(getStartingStatusRq, object : ConnectionListener<String> {
            override fun onFail(msg: String) {
                resetBtn()
                cancelLoading()
                showFailureMsg(msg)

            }

            override fun onSuccess(data: String) {
                resetBtn()
                cancelLoading()
                gotoNumberPad()
            }
        })
    }

    private fun gotoRemoteCall() {
        val intent = Intent(this, RemoteCallActivity::class.java)
        intent.putExtra(ACCOUNT_NAME, strAccountName)
        startActivity(intent)

    }

    private fun gotoNumberPad() {
        val intent = Intent(this@ChooseActivity, NumberPadActivity::class.java)
        intent.putExtra(ACCOUNT_NAME, strAccountName)
        intent.putExtra(STORE_NAME, strStoreName)
        startActivity(intent)

    }

    private fun lockBtn() {
        img_choose_remote.isEnabled = false
        tv_choose_remote.isEnabled = false
        img_choose_calling_pad.isEnabled = false
        tv_choose_calling_pad.isEnabled = false

    }

    private fun resetBtn() {
        img_choose_remote.isEnabled = true
        tv_choose_remote.isEnabled = true
        img_choose_calling_pad.isEnabled = true
        tv_choose_calling_pad.isEnabled = true

    }
}