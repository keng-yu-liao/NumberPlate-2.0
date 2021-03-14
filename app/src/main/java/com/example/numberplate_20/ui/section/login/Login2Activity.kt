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
        tv_login.setOnClickListener(this@Login2Activity)
    }

    private fun initViewModel() {
        val viewModelFactory = ViewModelFactory {
            LoginViewModel(ConnectionRepository)
        }
        loginViewModel = ViewModelProvider(this@Login2Activity, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.tv_login -> {
                when {
                    edt_login_account.text.isEmpty() -> {
                        LiaoDialog.getDialog(this@Login2Activity, getString(R.string.login_enter_account)).show()
                    }
                    edt_login_ps.text.isEmpty() -> {
                        LiaoDialog.getDialog(this@Login2Activity, getString(R.string.login_enter_password)).show()
                    }
                    else -> {
                        loginViewModel.login(edt_login_account.text.toString(), edt_login_ps.text.toString()) { isLogin, msg ->
                            if (isLogin) {
                                LiaoDialog.getDialog(this@Login2Activity, getString(R.string.login_loging)).show()
                            } else {
                                LiaoDialog.getDialog(this@Login2Activity, getString(R.string.login_fail) + " " + msg).show()
                            }
                        }
                    }
                }
            }
        }
    }
}