package com.example.converterlib.data.api

import com.example.converterlib.data.model.ApiResult
import retrofit2.http.GET

interface ApiService {
    //http://data.fixer.io/api/latest?access_key=0cc6c9fd211ecc4787b293ff65badb08
    @GET("latest?access_key=0cc6c9fd211ecc4787b293ff65badb08")
    suspend fun getLatest(): ApiResult
}