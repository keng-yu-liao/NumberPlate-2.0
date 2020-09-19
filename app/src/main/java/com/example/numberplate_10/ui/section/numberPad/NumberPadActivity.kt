package com.example.numberplate_10.ui.section.numberPad

import android.os.Bundle
import com.example.numberplate_10.R
import com.example.numberplate_10.common.ApiConfig.API.BASE_WEB_URL
import com.example.numberplate_10.common.ApiConfig.API.STORE_TABLE
import com.example.numberplate_10.common.TransDataCode.STORE_NAME
import com.example.numberplate_10.core.connection.ConnectionListener
import com.example.numberplate_10.core.connection.ConnectionManager
import com.example.numberplate_10.data.httpObj.GetLastWaitNumRq
import com.example.numberplate_10.ui.base.BaseActivity
import com.example.numberplate_10.utils.DialogUtil
import com.example.numberplate_10.utils.QRcodeUtil
import kotlinx.android.synthetic.main.activity_number_pad.*
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main

class NumberPadActivity : BaseActivity() {
    lateinit var strStoreName: String
    lateinit var updateQRcodeJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_pad)

        getExtra()
        initView()

    }

    override fun onDestroy() {
        super.onDestroy()
        updateQRcodeJob.cancel()

    }

    private fun getExtra() {
        strStoreName = intent.getStringExtra(STORE_NAME) ?: ""

    }

    private fun initView() {
        tv_number_pad_store_name.text = getString(R.string.number_pad_title).replace("*", strStoreName)

        updateQRcodeJob = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                sendGetLastWaitNum(STORE_TABLE)
                delay(1500)
            }

        }
    }

    private fun updateView(lastWaitNum: String) {
        //等待號碼+1
        val yourNum = lastWaitNum.let {
            val tmpInt = it.toInt() + 1
            tmpInt.toString()

        }
        tv_number_pad_current_num.text = getString(R.string.number_pad_hint).replace("*", yourNum)

        val qrContent = BASE_WEB_URL.replace("*YOUR_NUM", yourNum).replace("*STORE_TABLE_NAME", STORE_TABLE)
        val qrBitmap = QRcodeUtil.createQRcode(qrContent, img_number_qr_code.width, img_number_qr_code.height)
        img_number_qr_code.setImageBitmap(qrBitmap)

    }

    private fun sendGetLastWaitNum(storeTableName: String) {
        val getLastWaitNumRq = GetLastWaitNumRq(storeTableName)
        ConnectionManager.sendGetLastWaitNum(getLastWaitNumRq, object : ConnectionListener<String> {
            override fun onFail(msg: String) {
                DialogUtil.showDialog(this@NumberPadActivity, msg)

            }

            override fun onSuccess(data: String) {
                updateView(data)

            }

        })

    }

}