package com.example.tap_sample.network


import retrofit2.Response
import retrofit2.http.GET



interface ApiService {

    @GET("downloadTAPDB")
    suspend fun downloadDatabase(): Response<ResponseModel>

}