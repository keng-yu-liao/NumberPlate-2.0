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
}