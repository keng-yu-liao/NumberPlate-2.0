package com.example.numberplate_20.ui.section.login

import androidx.lifecycle.ViewModel
import com.example.numberplate_20.common.ConnectionCode
import com.example.numberplate_20.common.ConnectionCode.STATUS_SUCCESS
import com.example.numberplate_20.core.connection.ConnectionRepository
import com.example.numberplate_20.data.httpObj.Response
import retrofit2.Call
import retrofit2.Callback

class LoginViewModel(private val mConnectionRepository: ConnectionRepository) : ViewModel() {
    fun createFile(fileName: String, callback: (Boolean, String)->Unit) {
        mConnectionRepository.apiService().createFile(fileName).enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                callback.invoke(false, t.toString())
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        if (responseBody.status == STATUS_SUCCESS) {
                            callback.invoke(true, responseBody.data)
                        } else {
                            callback.invoke(false, responseBody.data)
                        }
                    }
                } else {
                    callback.invoke(false, ConnectionCode.WarningCode.STATUS_CONNECT_FAIL)
                }
            }
        })
    }
}