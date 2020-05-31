package com.example.numberplate_10.core.connection

interface ConnectionListener<T> {
    fun onFail(msg: String)

    fun onSuccess(t: T)

}