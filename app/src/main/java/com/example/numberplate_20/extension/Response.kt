package com.example.numberplate_20.extension

import com.example.numberplate_20.common.ConnectionCode
import com.example.numberplate_20.data.httpObj.Response

fun retrofit2.Response<Response.NullDataResponse>.process(
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
) {
    if (this.isSuccessful) {
        this.body()?.let {
            if (it.statusCode == ConnectionCode.STATUS_SUCCESS) {
                onSuccess.invoke()
            } else {
                onFail.invoke(it.message)
            }
        }
    } else {
        onFail.invoke(ConnectionCode.WarningCode.STATUS_CONNECT_FAIL)
    }
}
