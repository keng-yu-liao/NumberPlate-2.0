package com.example.numberplate_20.data.httpObj

data class WsRequest<T: WsActionObj>(
    val actionCode: String,
    val actionObj: T
)

data class WsResponse<T: WsActionObj>(
    val actionCode: String,
    val actionObj: T
)

sealed class WsActionObj {
    data class WaitNumRequest(
        val fileName: String
    ): WsActionObj()

    data class WaitNumResponse(
        val waitNumStr: String
    ): WsActionObj()
}

const val REQUEST_WAIT_NUM = "WS0001"
