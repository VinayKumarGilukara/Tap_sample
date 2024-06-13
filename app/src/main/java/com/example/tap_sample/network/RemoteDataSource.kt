package com.example.tap_sample.network

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private var apiService: ApiService) {

    suspend fun fetchData() = apiService.downloadDatabase()

}