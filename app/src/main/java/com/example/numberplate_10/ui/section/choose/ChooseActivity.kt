package com.example.numberplate_10.ui.section.choose

import android.os.Bundle
import android.view.View
import com.example.numberplate_10.R
import com.example.numberplate_10.common.ConnectionCode.STATUS_UNINITIAL
import com.example.numberplate_10.common.TransDataCode.ACCOUNT_NAME
import com.example.numberplate_10.core.connection.ConnectionManager
import com.example.numberplate_10.data.httpObj.GetStartingStatusRq
import com.example.numberplate_10.data.httpObj.GetStartingStatusRs
import com.example.numberplate_10.ui.base.BaseActivity
import com.example.numberplate_10.utils.DialogUtil
import kotlinx.android.synthetic.main.activity_choose.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseActivity : BaseActivity(), View.OnClickListener{
    lateinit var strAccountName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        getExtra()
        init()
    }

    private fun getExtra() {
        strAccountName = intent.getStringExtra(ACCOUNT_NAME) ?: ""
    }

    private fun init() {
        img_choose_remote.setOnClickListener(this)
        tv_choose_remote.setOnClickListener(this)
        img_choose_calling_pad.setOnClickListener(this)
        tv_choose_calling_pad.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.img_choose_calling_pad, R.id.tv_choose_calling_pad -> {
                lockBtn()
                showLoading()
                sendGetStartingStatus(strAccountName)
            }
        }
    }

    private fun sendGetStartingStatus(accountName: String) {
        val getStartingStatusRq = GetStartingStatusRq(accountName)
        ConnectionManager.getInstance().getStartingStatus(getStartingStatusRq.accountName).enqueue(object : Callback<GetStartingStatusRs> {
            override fun onFailure(call: Call<GetStartingStatusRs>, t: Throwable) {
                resetBtn()
                cancelLoading()
                t.message?.let { showFailureMsg(it) }
            }

            override fun onResponse(call: Call<GetStartingStatusRs>, response: Response<GetStartingStatusRs>) {
                resetBtn()
                cancelLoading()
                response.body()?.run {
                    checkStartingStatus(this)
                }
            }

        })

    }

    private fun checkStartingStatus(getStartingStatusRs: GetStartingStatusRs) {
        if(STATUS_UNINITIAL.equals(getStartingStatusRs.status)) {
            DialogUtil.showDialog(this, getString(R.string.choose_remote_first_hint))

        } else {


        }
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