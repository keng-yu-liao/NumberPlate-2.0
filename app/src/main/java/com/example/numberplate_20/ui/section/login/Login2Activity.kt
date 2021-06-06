package com.example.numberplate_20.ui.section.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.liaoutils.dialog.LiaoDialog
import com.example.numberplate_20.R
import com.example.numberplate_20.core.connection.ConnectionRepository
import com.example.numberplate_20.mvvm.ViewModelFactory
import com.example.numberplate_20.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login_2.*

class Login2Activity : BaseActivity(), View.OnClickListener {
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_2)

        initView()
        initViewModel()
    }

    private fun initView() {
        tv_enter.setOnClickListener(this@Login2Activity)
    }

    private fun initViewModel() {
        val viewModelFactory = ViewModelFactory {
            LoginViewModel(ConnectionRepository)
        }
        loginViewModel = ViewModelProvider(this@Login2Activity, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.tv_enter -> {
                when {
                    edt_account_name.text.isEmpty() -> {
                        LiaoDialog.getDialog(this@Login2Activity, getString(R.string.login_enter_name)).show()
                    }

                    else -> {
                    }
                }
            }
        }
    }
}