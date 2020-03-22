package com.example.numberplate_10.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.numberplate_10.R

class LoginActivity : AppCompatActivity(), View.OnClickListener{

    private var accountEdt: EditText? = null
    private var passwordEdt: EditText? = null
    private var loginBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initElements()
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        when (id) {

        }
    }

    private fun initElements() {
        accountEdt = findViewById<EditText>(R.id.login_account_edt)
        passwordEdt = findViewById<EditText>(R.id.login_ps_edt)
        loginBtn = findViewById<Button>(R.id.login_btn)
    }
}
