package com.example.numberplate_20.data.httpObj

sealed class  Response {
    data class DataResponse (
            val statusCode: String,
            val message: String,
            val data: String
    ) : Response()

    data class NullDataResponse (
            val statusCode: String,
            val message: String
    ) : Response()
}