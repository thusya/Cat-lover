package com.thusee.core_network.api

import com.thusee.core_network.model.CatResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("images/search")
    suspend fun getImages(
//        @Header("x-api-key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("has_breeds") hasBreeds: Int = 1
    ): List<CatResponse>
}