package com.example.numberplate_20.ui.base

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.numberplate_20.R
import com.example.numberplate_20.utils.DialogUtil

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    protected lateinit var progressAlertDialog: AlertDialog

    override fun onBackPressed() {
        DialogUtil.showDialogWithPosListener(
                this,
                getString(R.string.all_close_app),
                DialogInterface.OnClickListener { _, _ -> finishAffinity() }
        )
    }

    protected fun showLoading(msg: String) {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_layout_progress, null)
        view.findViewById<TextView>(R.id.progress_dialog_title).text = msg

        progressAlertDialog = AlertDialog.Builder(this).setView(view).show()
    }

    protected fun cancelLoading() {
        if (progressAlertDialog.isShowing) {
            progressAlertDialog.dismiss()
        }
    }

    protected fun showFailureMsg(msg: String) {
        val errorDialog = AlertDialog.Builder(this)
                            .setTitle(getString(R.string.dialog_error_title))
                            .setMessage(msg)
                            .create()
        errorDialog.show()
    }
}