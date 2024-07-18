package com.thusee.core_data.fakes

import com.thusee.core_network.api.ApiService
import com.thusee.core_network.model.CatResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FakeCatApiService(
    private val fakeResponse: List<CatResponse>,
    private val serviceDelay: Long,
    private val testScope: CoroutineScope,
    private val isThrowException: Boolean = false,
) : ApiService {

    override suspend fun getImages(
        limit: Int, page: Int, hasBreeds: Int
    ): List<CatResponse> {
        testScope.launch {
            delay(serviceDelay)
        }

        if (isThrowException) throw Exception("")

        return fakeResponse
    }
}

