package com.example.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SaveGramApi {
    @POST("api/fetch")
    suspend fun fetchMedia(@Body request: FetchRequest): Response<FetchResponse>
}
