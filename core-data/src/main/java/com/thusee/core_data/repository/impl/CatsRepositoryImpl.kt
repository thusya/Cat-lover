package com.thusee.core_data.repository.impl

import com.thusee.core_data.async.AsyncOperation
import com.thusee.core_data.async.CatRepoAsyncEvents
import com.thusee.core_data.di.ApplicationScope
import com.thusee.core_data.di.DefaultDispatcher
import com.thusee.core_data.model.Cat
import com.thusee.core_data.repository.CatsRepository
import com.thusee.core_database.db.CatDao
import com.thusee.core_database.model.CatEntity
import com.thusee.core_network.api.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val catDao: CatDao,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : CatsRepository {

    override val cats: Flow<List<Cat>> = catDao
        .getCats()
        .map {
            it.map { catModel ->
                Cat(
                    id = catModel.id,
                    breed = catModel.breed,
                    image = catModel.image,
                    description = catModel.description ?: "",
                    isFavorite = catModel.isFavorite ?: false
                )
            }
        }

    private val _operationStatus: MutableSharedFlow<AsyncOperation<CatRepoAsyncEvents>> =
        MutableSharedFlow()

    override val operationStatus: Flow<AsyncOperation<CatRepoAsyncEvents>> = _operationStatus

    override suspend fun fetchCats() {
        _operationStatus.emit(AsyncOperation.Loading)
        scope.launch {
            try {
                val catsResponse = withContext(defaultDispatcher) {
                    apiService.getImages(
                        limit = 20,
                        hasBreeds = 1
                    )
                }
                if (catsResponse.isEmpty()) {
                    _operationStatus.emit(
                        AsyncOperation.Failure(
                            CatRepoAsyncEvents.LoadCatsFailed,
                            Throwable("Error, No cats in server!")
                        )
                    )
                }

                val catModels = catsResponse
                    .filter {
                        it.breeds.isNotEmpty()
                    }
                    .map {
                        CatEntity(
                            it.id,
                            it.breeds[0].name,
                            it.url,
                            it.breeds[0].description,
                            false,
                        )
                    }

                withContext(defaultDispatcher) {
                    catDao.upsertCats(catModels)
                }
                _operationStatus.emit(AsyncOperation.Success(CatRepoAsyncEvents.LoadCatsSuccess))
            } catch (e: Exception) {
                _operationStatus.emit(
                    AsyncOperation.Failure(
                        CatRepoAsyncEvents.LoadCatsFailed,
                        e
                    )
                )
            }
        }
    }

    override suspend fun onFavoriteCat(cat: Cat) {
        _operationStatus.emit(AsyncOperation.Loading)

        val catModel = CatEntity(
            cat.id,
            cat.breed,
            cat.image,
            cat.description,
            !cat.isFavorite,
        )

        try {
            scope.launch(defaultDispatcher) { catDao.upsertCat(catModel) }
            if (catModel.isFavorite == true) {
                _operationStatus.emit(AsyncOperation.Success(CatRepoAsyncEvents.FavoriteCatsSuccess))
            } else {
                _operationStatus.emit(AsyncOperation.Success(CatRepoAsyncEvents.UnFavoriteCatsSuccess))
            }
        } catch (e: Exception) {
            _operationStatus.emit(
                AsyncOperation.Failure(
                    CatRepoAsyncEvents.FavoriteCatsFailed,
                    e
                )
            )
        }
    }
}