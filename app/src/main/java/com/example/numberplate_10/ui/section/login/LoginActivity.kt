package com.example.numberplate_10.ui.section.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.numberplate_10.R
import com.example.numberplate_10.common.ConnectionCode.STATUS_SUCCESS
import com.example.numberplate_10.common.TransDataCode.ACCOUNT_NAME
import com.example.numberplate_10.core.connection.ConnectionManager
import com.example.numberplate_10.data.httpObj.LoginRq
import com.example.numberplate_10.data.httpObj.LoginRs
import com.example.numberplate_10.ui.base.BaseActivity
import com.example.numberplate_10.ui.section.choose.ChooseActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

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
        val id = view?.id
        when (id) {
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
        ConnectionManager.getInstance().login(loginRq.accountName, loginRq.accountPassword).enqueue(object : Callback<LoginRs> {
            override fun onFailure(call: Call<LoginRs>, t: Throwable) {
                resetBtn()
                cancelLoading()
                t.message?.let { showFailureMsg(it) }
            }

            override fun onResponse(call: Call<LoginRs>, response: Response<LoginRs>) {
                resetBtn()
                cancelLoading()
                response.body()?.run {
                    login(this)
                }
            }

        })
    }

    private fun login(loginRs: LoginRs) {
        if (STATUS_SUCCESS == loginRs.status) {
            val intent = Intent(this, ChooseActivity::class.java)
            intent.putExtra(ACCOUNT_NAME, strAccountName)
            startActivity(intent)
            finish()

        } else {
            showFailureMsg(getString(R.string.login_fail))

        }

    }

    private fun lockBtn() {
        login_btn.isEnabled = false
    }

    private fun resetBtn() {
        login_btn.isEnabled = true
    }
}
