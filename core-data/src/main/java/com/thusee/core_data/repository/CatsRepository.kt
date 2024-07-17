package com.thusee.core_data.repository

import com.thusee.core_data.model.Cat
import com.thusee.core_data.utils.ResultOf
import kotlinx.coroutines.flow.Flow

interface CatsRepository {
    val cats: Flow<List<Cat>>
    suspend fun fetchCats(): ResultOf<Unit>
    suspend fun onFavoriteCat(cat: Cat): ResultOf<Unit>
}