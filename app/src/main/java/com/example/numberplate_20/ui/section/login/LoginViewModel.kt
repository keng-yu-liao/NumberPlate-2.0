package com.example.numberplate_20.ui.section.login

import androidx.lifecycle.ViewModel
import com.example.numberplate_20.core.connection.ConnectionRepository
import com.example.numberplate_20.extension.process
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import java.io.IOException

class LoginViewModel(private val mConnectionRepository: ConnectionRepository) : ViewModel() {

    fun login(account: String, password: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                mConnectionRepository.apiService().login(
                        account = account,
                        password = password
                ).execute().process(
                        onSuccess = {
                            // TODO  go to next page
                        },
                        onFail = {
                            // TODO fail process
                        }
                )
            } catch (e: IOException) {
                // TODO exception process
            }
        }
    }
}