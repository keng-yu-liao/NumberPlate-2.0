package com.example.numberplate_20.ui.section.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.liaoutils.dialog.LiaoDialog
import com.example.numberplate_20.R
import com.example.numberplate_20.core.connection.ConnectionRepository
import com.example.numberplate_20.mvvm.ViewModelFactory
import com.example.numberplate_20.ui.base.BaseActivity
import com.example.numberplate_20.ui.section.operation.OperationActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViewModel()
        initView()
    }

    private fun initView() {
        tv_enter.setOnClickListener(this@LoginActivity)
    }

    private fun initViewModel() {
        val viewModelFactory = ViewModelFactory {
            LoginViewModel(ConnectionRepository)
        }
        loginViewModel = ViewModelProvider(this@LoginActivity, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.tv_enter -> {
                if (edt_account_name.text.isEmpty()) {
                    LiaoDialog.getDialog(this@LoginActivity, getString(R.string.login_enter_name)).show()
                } else {
                    val fileNameStr = edt_account_name.text.toString() + ".txt"

                    loginViewModel.createFile(fileNameStr) { isSuccess, data ->
                        if (isSuccess) {
                            val intent = Intent(this@LoginActivity, OperationActivity::class.java).apply {
                                putExtra("FileName", fileNameStr)
                            }
                            startActivity(intent)
                        } else {
                            LiaoDialog.getDialog(this@LoginActivity, data).show()
                        }
                    }
                }
            }
        }
    }
}