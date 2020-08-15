package com.example.numberplate_10.ui.section.numberPad

import android.os.Bundle
import com.example.numberplate_10.R
import com.example.numberplate_10.common.TransDataCode.STORE_NAME
import com.example.numberplate_10.ui.base.BaseActivity
import com.example.numberplate_10.utils.QRcodeUtil
import kotlinx.android.synthetic.main.activity_number_pad.*

class NumberPadActivity : BaseActivity() {
    lateinit var strStoreName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_pad)

        getExtra()
        initView()

    }

    fun getExtra() {
        strStoreName = intent.getStringExtra(STORE_NAME) ?: ""

    }

    fun initView() {
        tv_number_pad_store_name.text = getString(R.string.number_pad_title).replace("*", strStoreName)
        img_number_qr_code.post(Runnable {
            val bitmap = QRcodeUtil.createQRcode("1230", img_number_qr_code.width, img_number_qr_code.height)
            img_number_qr_code.setImageBitmap(bitmap)

        })
    }

}