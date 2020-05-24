package com.example.numberplate_10.utils

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.example.numberplate_10.R
import com.example.numberplate_10.ui.base.BaseActivity

class DialogUtil {
    companion object {

        fun showDialog(act: BaseActivity, msg: String) {
            val builder = AlertDialog.Builder(act)
            builder.setMessage(msg)
            builder.create().show()
        }

        fun showDialogWithPosListener(act: BaseActivity, msg: String, positiveListener: DialogInterface.OnClickListener) {
            val builder = AlertDialog.Builder(act)
            builder.setMessage(msg)
            builder.setPositiveButton(act.getString(R.string.all_yes), positiveListener)
            builder.create().show()

        }

    }
}