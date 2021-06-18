package com.example.numberplate_20.manager

object NumManager {

    fun getLastNum(numArr: IntArray): String {
        return (numArr.max()?.plus(1)).toString()
    }
}