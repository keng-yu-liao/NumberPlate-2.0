package com.example.numberplate_10.ui.section.login

import android.os.Bundle
import android.view.View
import com.example.numberplate_10.R
import com.example.numberplate_10.core.connection.ConnectionManager
import com.example.numberplate_10.data.httpObj.LoginRq
import com.example.numberplate_10.data.httpObj.LoginRs
import com.example.numberplate_10.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class LoginActivity : BaseActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        when (id) {
            R.id.login_btn -> {
                showLoading(getString(R.string.login_loging))

                val loginRq = LoginRq(accountName = edt_login_account.text.toString(), accountPassword = edt_login_ps.text.toString())
                ConnectionManager.getInstance().login(loginRq.accountName, loginRq.accountPassword).enqueue(object : Callback<LoginRs> {
                    override fun onFailure(call: Call<LoginRs>, t: Throwable) {
                        cancelLoading()
                        t.message?.let { showFailureMsg(it) }
                    }

                    override fun onResponse(call: Call<LoginRs>, response: Response<LoginRs>) {
                        cancelLoading()
                        response.body()?.run {
                            login(this)
                        }
                    }

                })
            }
        }
    }

    private fun init() {
        login_btn.setOnClickListener(this)
    }

    private fun login(loginRs: LoginRs) {

    }

}
