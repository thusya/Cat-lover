package com.thusee.core_data.repository

import com.thusee.core_data.async.AsyncOperation
import com.thusee.core_data.async.CatRepoAsyncEvents
import com.thusee.core_data.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatsRepository {
    val cats: Flow<List<Cat>>
    val operationStatus: Flow<AsyncOperation<CatRepoAsyncEvents>>
    suspend fun fetchCats()
    suspend fun onFavoriteCat(cat: Cat)
}