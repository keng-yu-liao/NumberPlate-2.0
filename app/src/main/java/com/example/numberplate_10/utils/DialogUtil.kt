package com.example.numberplate_10.utils

import androidx.appcompat.app.AlertDialog
import com.example.numberplate_10.ui.base.BaseActivity

class DialogUtil {
    companion object {

        fun showDialog(act: BaseActivity, msg: String) {
            val builder = AlertDialog.Builder(act)
            builder.setMessage(msg)
            builder.create().show()
        }

    }
}