package com.thusee.core_network.api

import com.thusee.core_network.model.CatResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("images/search")
    suspend fun getImages(
        @Query("limit") limit: Int,
        @Query("page") page: Int = 1,
        @Query("has_breeds") hasBreeds: Int = 1
    ): List<CatResponse>
}