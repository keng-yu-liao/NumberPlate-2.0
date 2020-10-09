package com.example.numberplate_10.ui.section.login

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.numberplate_10.R
import com.example.numberplate_10.common.ApiConfig
import com.example.numberplate_10.common.TransDataCode.ACCOUNT_NAME
import com.example.numberplate_10.common.TransDataCode.STORE_NAME
import com.example.numberplate_10.core.connection.ConnectionListener
import com.example.numberplate_10.core.connection.ConnectionManager
import com.example.numberplate_10.data.httpObj.LoginRq
import com.example.numberplate_10.data.httpObj.LoginRsData
import com.example.numberplate_10.ui.base.BaseActivity
import com.example.numberplate_10.ui.section.choose.ChooseActivity
import com.example.numberplate_10.utils.DialogUtil
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

        //第一次開啟app顯示提示視窗
        if (ApiConfig.API.APP_CONFIG.getFirstOpen()) {
            DialogUtil.showDialogWithPosNegListener(this@LoginActivity, getString(R.string.dialog_first_open_hint),
                    DialogInterface.OnClickListener { _, _ ->
                        callSendEmail()
                        ApiConfig.API.APP_CONFIG.setFirstOpen()
                    },
                    DialogInterface.OnClickListener { _, _ -> }
                    )
        }
    }

    private fun callSendEmail() {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(ApiConfig.API.MY_EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.dialog_email_subject_default))
            putExtra(Intent.EXTRA_TEXT, getString(R.string.dialog_email_content_default))
            type = "plain/text"
        }

        startActivity(emailIntent)
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
        ConnectionManager.sendLogin(loginRq, object : ConnectionListener<LoginRsData> {
            override fun onFail(msg: String) {
                resetBtn()
                cancelLoading()

                if (TextUtils.isEmpty(msg)) {
                    showFailureMsg(getString(R.string.login_fail))

                } else {
                    showFailureMsg(msg)

                }
            }

            override fun onSuccess(data: LoginRsData) {
                cancelLoading()
                ApiConfig.API.APP_CONFIG.setStoreTable(data.storeTableName)

                val intent = Intent(this@LoginActivity, ChooseActivity::class.java).apply {
                    putExtra(ACCOUNT_NAME, strAccountName)
                    putExtra(STORE_NAME, data.storeName)
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
