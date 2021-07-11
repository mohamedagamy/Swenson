package com.example.converterlib.data.repo

import com.example.converterlib.data.api.ApiService
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getLatest() = apiService.getLatest()

}
