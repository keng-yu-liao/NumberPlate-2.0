package com.example.numberplate_20.ui.section.operation

import android.os.Bundle
import android.view.View.GONE
import androidx.lifecycle.ViewModelProvider
import com.example.numberplate_20.R
import com.example.numberplate_20.core.connection.ConnectionRepository
import com.example.numberplate_20.mvvm.ViewModelFactory
import com.example.numberplate_20.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_operation.*

class OperationActivity : BaseActivity() {
    private lateinit var operationViewModel: OperationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation)

        initViewModel()
        initView()
    }

    private fun initView() {
        rcv_uncall.visibility = GONE

        val fileName = intent.getStringExtra("FileName")
        operationViewModel.requestUncallNum(fileName) { isSuccess, uncallNum ->
            if (isSuccess) {
                if (uncallNum.isEmpty()) {
                    setUncallNum("1")
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

    private fun setUncallNum(uncallNum: String) {
        tv_operation_display_pad_num.text = uncallNum
    }
}