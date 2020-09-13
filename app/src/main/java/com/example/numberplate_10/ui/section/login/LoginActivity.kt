package com.example.numberplate_10.ui.section.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.numberplate_10.R
import com.example.numberplate_10.common.TransDataCode.ACCOUNT_NAME
import com.example.numberplate_10.common.TransDataCode.STORE_NAME
import com.example.numberplate_10.core.connection.ConnectionListener
import com.example.numberplate_10.core.connection.ConnectionManager
import com.example.numberplate_10.data.httpObj.LoginRq
import com.example.numberplate_10.ui.base.BaseActivity
import com.example.numberplate_10.ui.section.choose.ChooseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    //需要傳值，故宣告為全域變數
    lateinit var strAccountName: String
    lateinit var strAccountPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }


    private fun init() {
        login_btn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.login_btn -> {
                lockBtn()
                showLoading(getString(R.string.login_loging))
                strAccountName = edt_login_account.text.toString()
                strAccountPassword = edt_login_ps.text.toString()
                sendLogin()
            }
        }
    }

    private fun sendLogin() {
        val loginRq = LoginRq(strAccountName, strAccountPassword)
        ConnectionManager.sendLogin(loginRq, object : ConnectionListener<String> {
            override fun onFail(msg: String) {
                resetBtn()
                cancelLoading()

                if (TextUtils.isEmpty(msg)) {
                    showFailureMsg(getString(R.string.login_fail))

                } else {
                    showFailureMsg(msg)

                }
            }

            override fun onSuccess(data: String) {
                val intent = Intent(this@LoginActivity, ChooseActivity::class.java).apply {
                    putExtra(ACCOUNT_NAME, strAccountName)
                    putExtra(STORE_NAME, data)
                }

                startActivity(intent)
                finish()
            }
        })

    }

    private fun lockBtn() {
        login_btn.isEnabled = false
    }

    private fun resetBtn() {
        login_btn.isEnabled = true
    }
}
