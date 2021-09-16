package com.gogolook.homework.model.api

import com.gogolook.homework.model.api.model.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    @GET(".")
    suspend fun getImages(
        @QueryMap map: Map<String, String>
    ): Response<Images>
}