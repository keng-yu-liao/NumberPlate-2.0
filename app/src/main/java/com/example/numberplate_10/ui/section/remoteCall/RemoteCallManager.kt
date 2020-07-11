package com.example.numberplate_10.ui.section.remoteCall

import com.example.numberplate_10.data.httpObj.RemoteRowData

class RemoteCallManager {
    private var mRemoteCallList = ArrayList<RemoteRowData>()

    fun setRemoteCallList(remoteCallList: ArrayList<RemoteRowData>) {
        mRemoteCallList.clear()
        mRemoteCallList.addAll(remoteCallList)

    }

    fun getRemoteCallList(): ArrayList<RemoteRowData> {
        return this.mRemoteCallList

    }

}