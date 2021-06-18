package com.example.numberplate_20.ui.section.operation

import androidx.lifecycle.ViewModel
import com.example.numberplate_20.common.ConnectionCode
import com.example.numberplate_20.core.connection.ConnectionRepository
import com.example.numberplate_20.data.httpObj.Response
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import retrofit2.Call
import retrofit2.Callback

class OperationViewModel(private val mConnectionRepository: ConnectionRepository) : ViewModel() {
    fun requestUncallNumRepeatedly(fileName: String, callback: (Boolean, String)->Unit) : Job {

        return GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                mConnectionRepository.apiService().requestUncallNum(fileName).enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        callback.invoke(false, t.toString())
                    }

                    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                        if (response.isSuccessful) {
                            response.body()?.let { responseBody ->
                                if (responseBody.status == ConnectionCode.STATUS_SUCCESS) {
                                    callback.invoke(true, responseBody.data)
                                }
                            }
                        } else {
                            callback.invoke(false, ConnectionCode.WarningCode.STATUS_CONNECT_FAIL)
                        }
                    }
                })

                delay(3000)
            }
        }
    }
}