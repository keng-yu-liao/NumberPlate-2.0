package com.example.numberplate_10.core.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceProcess {
    companion object{
        private lateinit var pref: SharedPreferences
        private const val FIRST_OPEN_APP = "FIRST_OPEN_APP"

        fun initSharedPreferences(context: Context) {
            pref = context.getSharedPreferences("number_plate10_pref", Context.MODE_PRIVATE)
        }

        fun setFirstOpenApp() {
            pref.edit().putBoolean(FIRST_OPEN_APP, false).apply()
        }

        fun getFirstOpenApp(): Boolean {
            return pref.getBoolean(FIRST_OPEN_APP, true)
        }
    }
}