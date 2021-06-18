package com.example.numberplate_20.ui.section.operation

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.liaoutils.dialog.LiaoDialog
import com.example.numberplate_20.R
import com.example.numberplate_20.core.connection.ConnectionRepository
import com.example.numberplate_20.manager.NumManager
import com.example.numberplate_20.mvvm.ViewModelFactory
import com.example.numberplate_20.ui.base.BaseActivity
import com.example.numberplate_20.utils.QRcodeUtil
import kotlinx.android.synthetic.main.activity_operation.*
import kotlinx.coroutines.Job

//每3秒做一次更新
class OperationActivity : BaseActivity(), OperationAdapter.OperationAdapterListener {
    private lateinit var operationViewModel: OperationViewModel
    private lateinit var updateJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation)

        showLoading(getString(R.string.all_loading))
        initViewModel()
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        updateJob.cancel()
    }

    override fun onNumClick(clickedNum: String) {

    }

    private fun initView() {
        rcv_uncall.apply {
            visibility = GONE
            layoutManager = GridLayoutManager(this@OperationActivity, 5, GridLayoutManager.VERTICAL, false)
            adapter = OperationAdapter(this@OperationActivity, intArrayOf())
        }

        val fileName = intent.getStringExtra("FileName")
        fileName?.let { fileNameStr ->
            updateJob = operationViewModel.requestUncallNumRepeatedly(fileNameStr) { isSuccess, data ->
                cancelLoading()
                if (isSuccess) {
                    if (data.isEmpty()) {
                        setUncallNum("1")
                        setUncallNumRcv(intArrayOf())

                    } else {
                        val uncallArr = data.split("*").map {
                            it.toInt()
                        }.toIntArray()
                        setUncallNum(NumManager.getLastNum(uncallArr))
                        setUncallNumRcv(uncallArr)
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

    private fun setUncallNumRcv(uncallNumArr: IntArray) {
        if (uncallNumArr.isEmpty()) {
            rcv_uncall.visibility = GONE
            tv_no_num.visibility = VISIBLE
        } else {
            rcv_uncall.visibility = VISIBLE
            tv_no_num.visibility = GONE
            (rcv_uncall.adapter as OperationAdapter).setNumArr(uncallNumArr)
        }
    }

    private fun setUncallNum(uncallNum: String) {
        tv_operation_display_pad_num.text = uncallNum

        val qrBitmap = QRcodeUtil.createQRcode(uncallNum, img_operation_display_pad.width, img_operation_display_pad.height)
        img_operation_display_pad.setImageBitmap(qrBitmap)
    }
}