package com.example.numberplate_20.ui.section.login

import androidx.lifecycle.ViewModel
import com.example.numberplate_20.core.connection.ConnectionRepository
import com.example.numberplate_20.extension.process
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import java.io.IOException

class LoginViewModel(private val mConnectionRepository: ConnectionRepository) : ViewModel() {

    fun login(account: String, password: String, onSuccess: ()->Unit, onFail: (String)->Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                mConnectionRepository.apiService().login(
                        account = account,
                        password = password
                ).execute().process(
                        onSuccess = {
                            launch(Dispatchers.Main) {
                                onSuccess.invoke()
                            }
                        },
                        onFail = {
                            launch(Dispatchers.Main) {
                                onFail.invoke(it)
                            }
                        }
                )
            } catch (e: IOException) {
                launch(Dispatchers.Main) {
                    e.message?.let { onFail.invoke(it) }
                }
            }
        }
    }
}