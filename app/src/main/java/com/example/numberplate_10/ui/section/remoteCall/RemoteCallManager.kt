package com.example.numberplate_10.ui.section.remoteCall

import com.example.numberplate_10.data.httpObj.RemoteRowData
import kotlinx.coroutines.Job

class RemoteCallManager {
    private var mRemoteCallList = ArrayList<RemoteRowData>()
    private var mJob = Job()

    fun setRemoteCallList(remoteCallList: ArrayList<RemoteRowData>) {
        mRemoteCallList.clear()
        mRemoteCallList.addAll(remoteCallList)

    }

    fun getRemoteCallList(): ArrayList<RemoteRowData> {
        return this.mRemoteCallList

    }

    fun setRemoteCallJob(job: Job) {
        mJob = job

    }

    fun stopRemoteCallJob() {
        mJob.cancel()

    }

}