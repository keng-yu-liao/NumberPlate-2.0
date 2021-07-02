package com.example.numberplate_20.manager

object NumManager {

    fun getLastNum(numList: MutableList<Int>): String {
        return (numList.max()?.plus(1)).toString()
    }
}