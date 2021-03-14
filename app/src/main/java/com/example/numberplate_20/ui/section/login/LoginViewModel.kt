package com.example.numberplate_20.ui.section.login

import androidx.lifecycle.ViewModel
import com.example.numberplate_20.common.ConnectionCode.STATUS_SUCCESS
import com.example.numberplate_20.core.connection.ConnectionRepository
import com.example.numberplate_20.data.httpObj.Response
import retrofit2.Call
import retrofit2.Callback

class LoginViewModel(private val mConnectionRepository: ConnectionRepository) : ViewModel() {
    fun login(accountName: String, accountPassword: String, callback: (Boolean, String)->Unit) {
        mConnectionRepository.apiService().login(accountName, accountPassword).enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                callback.invoke(false, t.toString())
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.body()?.status == STATUS_SUCCESS) {
                    callback.invoke(true, response.body()?.data!!)
                } else {
                    callback.invoke(false, response.body()?.data!!)
                }
            }
        })
    }
}