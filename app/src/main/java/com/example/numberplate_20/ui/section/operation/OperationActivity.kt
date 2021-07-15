package com.example.numberplate_20.ui.section.operation

import android.os.Bundle
import android.view.View.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.liaoutils.dialog.LiaoDialog
import com.example.numberplate_20.BuildConfig
import com.example.numberplate_20.R
import com.example.numberplate_20.core.connection.ConnectionRepository
import com.example.numberplate_20.mvvm.ViewModelFactory
import com.example.numberplate_20.ui.base.BaseActivity
import com.example.numberplate_20.utils.QRcodeUtil
import kotlinx.android.synthetic.main.activity_operation.*
import kotlinx.coroutines.Job

//每3秒做一次更新
class OperationActivity : BaseActivity(), OperationAdapter.OperationAdapterListener {
    private lateinit var operationViewModel: OperationViewModel
    private lateinit var updateJob: Job
    private var fileName: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation)

        showLoading(getString(R.string.all_loading))
        initViewModel()
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        fileName?.let { operationViewModel.deleteFile(it) }
        updateJob.cancel()
    }

    override fun onNumClick(clickedNum: String) {
        fileName?.let { fileNameStr ->
            operationViewModel.callNum(fileNameStr, clickedNum) { isSuccess, data ->
                if (!isSuccess) {
                    LiaoDialog.getDialog(this@OperationActivity, data).show()
                }
            }
        }
    }

    private fun initView() {
        LiaoDialog.getDialog(this@OperationActivity, getString(R.string.operation_more_40)).show()

        rcv_uncall.apply {
            visibility = GONE
            layoutManager = GridLayoutManager(this@OperationActivity, 5, GridLayoutManager.VERTICAL, false)
            adapter = OperationAdapter(this@OperationActivity, arrayListOf())
        }

        fileName = intent.getStringExtra("FileName")
        fileName?.let { fileNameStr ->
            updateJob = operationViewModel.requestUncallNumRepeatedly(fileNameStr) { isSuccess, data ->
                cancelLoading()
                if (isSuccess) {
                    val dataList = data.split("&".toRegex())

                    // 起始時
                    if (dataList[0] == "0") {
                        setUncallNum(fileNameStr, "1")
                        setUncallNumRcv(mutableListOf())
                    } else {
                        val uncallNumList = dataList[0].split("*").map {
                            it.toInt()
                        }.toMutableList()

                        // 等待號碼大於40，會結束更新排程
                        if (Integer.parseInt(dataList[1]) > 40) {
                            updateJob.cancel()
                            setUncallNum(fileNameStr, "")
                            LiaoDialog.getDialog(this@OperationActivity, getString(R.string.operation_more_40)).show()
                        } else {
                            setUncallNum(fileNameStr, (Integer.parseInt(dataList[1]) + 1).toString())
                            setUncallNumRcv(uncallNumList)
                        }
                    }
                } else {
                    LiaoDialog.getDialog(this@OperationActivity, data).show()
                }
            }
        }
    }

    private fun initViewModel() {
        val viewModelFactory = ViewModelFactory {
            OperationViewModel(ConnectionRepository)
        }
        operationViewModel = ViewModelProvider(this@OperationActivity, viewModelFactory).get(OperationViewModel::class.java)
    }

    private fun setUncallNumRcv(uncallNumArr: MutableList<Int>) {
        if (uncallNumArr.isEmpty()) {
            rcv_uncall.visibility = GONE
            tv_no_num.visibility = VISIBLE
        } else {
            rcv_uncall.visibility = VISIBLE
            tv_no_num.visibility = GONE
            (rcv_uncall.adapter as OperationAdapter).setNumArr(uncallNumArr)
        }
    }

    private fun setUncallNum(fileName: String, uncallNum: String) {
        if (uncallNum.isEmpty()) {
            tv_operation_display_pad_num.text = "X"
            img_operation_display_pad.visibility = INVISIBLE
        } else {
            tv_operation_display_pad_num.text = uncallNum

            val qrCodeContent = BuildConfig.BASE_WEB_URL.replace("*FILE_NAME", fileName).replace("*YOUR_NUM", uncallNum)
            val qrBitmap = QRcodeUtil.createQRcode(qrCodeContent, img_operation_display_pad.width, img_operation_display_pad.height)
            img_operation_display_pad.setImageBitmap(qrBitmap)
        }
    }
}