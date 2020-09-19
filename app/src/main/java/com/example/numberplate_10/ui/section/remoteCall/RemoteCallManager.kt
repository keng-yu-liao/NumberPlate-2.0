package com.example.numberplate_10.ui.section.remoteCall

import kotlinx.coroutines.Job

class RemoteCallManager {
    private var mRemoteCallNumList = ArrayList<String>()
    private var mJob = Job()

    fun setRemoteCallList(remoteCallNumList: ArrayList<String>) {
        mRemoteCallNumList.clear()
        mRemoteCallNumList.addAll(remoteCallNumList)

    }

    fun getRemoteCallList(): ArrayList<String> {
        return this.mRemoteCallNumList

    }

    fun setRemoteCallJob(job: Job) {
        mJob = job

    }

    fun stopRemoteCallJob() {
        mJob.cancel()

    }

}