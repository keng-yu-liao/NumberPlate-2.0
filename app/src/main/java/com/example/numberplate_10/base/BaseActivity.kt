package com.example.numberplate_10.base

import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.numberplate_10.R

class BaseActivity : AppCompatActivity() {
    protected lateinit var progressAlertDialog: AlertDialog

    protected fun showLoading(msg: String) {
        if (!progressAlertDialog.isShowing) {
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_layout_progress, null)
            view.findViewById<TextView>(R.id.progress_dialog_title).setText(msg)

            progressAlertDialog = AlertDialog.Builder(this).setView(view).show()
        }
    }

    protected fun cancelLoading() {
        if (progressAlertDialog.isShowing) {
            progressAlertDialog.dismiss()
        }
    }
}