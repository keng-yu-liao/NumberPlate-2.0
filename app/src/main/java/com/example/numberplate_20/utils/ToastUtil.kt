package com.example.numberplate_20.utils

import android.view.Gravity
import android.widget.Toast
import com.example.numberplate_20.ui.base.BaseActivity

class ToastUtil {
    companion object {

        fun showToast(act: BaseActivity, msg: String) {
           val toast = Toast.makeText(act, msg, Toast.LENGTH_SHORT)
           toast.setGravity(Gravity.CENTER, 0, 0)

            toast.show()
        }

    }
}