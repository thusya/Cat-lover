package com.thusee.core_data.repository.impl

import com.thusee.core_data.async.AsyncOperation
import com.thusee.core_data.async.CatRepoAsyncEvents
import com.thusee.core_data.di.ApplicationScope
import com.thusee.core_data.di.DefaultDispatcher
import com.thusee.core_data.model.Cat
import com.thusee.core_data.repository.CatsRepository
import com.thusee.core_data.utils.toCat
import com.thusee.core_data.utils.toCatEntity
import com.thusee.core_database.db.CatDao
import com.thusee.core_network.api.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
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
        .map { list ->
            list.map { it.toCat() }
        }

    private val _operationStatus: MutableSharedFlow<AsyncOperation<CatRepoAsyncEvents>> = MutableSharedFlow(replay = 1)

    override val operationStatus: Flow<AsyncOperation<CatRepoAsyncEvents>> = _operationStatus

    override suspend fun fetchCats() {
        _operationStatus.emit(AsyncOperation.Loading)
        scope.launch {
            try {
                val catsResponse = withContext(defaultDispatcher) {
                    apiService.getImages(
                        limit = 20
                    )
                }

                if (catsResponse.isEmpty()) {
                    _operationStatus.emit(
                        AsyncOperation.Failure(
                            CatRepoAsyncEvents.LoadCatsFailed,
                            Throwable("Error, No cats in server!")
                        )
                    )
                } else {
                    val catModels = catsResponse
                        .filter {
                            it.breeds.isNotEmpty()
                        }
                        .map { it.toCatEntity() }

                    withContext(defaultDispatcher) {
                        catDao.upsertCats(catModels)
                    }
                    _operationStatus.emit(AsyncOperation.Success(CatRepoAsyncEvents.LoadCatsSuccess))
                }
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

        val catModel = cat.copy(isFavorite = !cat.isFavorite).toCatEntity()

        try {
            scope.launch(defaultDispatcher) { catDao.upsertCat(catModel) }
            _operationStatus.emit(AsyncOperation.Success(CatRepoAsyncEvents.FavoriteCatsSuccess))
        }catch (e: Exception) {
            _operationStatus.emit(
                AsyncOperation.Failure(
                    CatRepoAsyncEvents.FavoriteCatsFailed,
                    e
                )
            )
        }
    }

    override fun getCatById(catId: String): Flow<Cat> = flow {
        try {
            emitAll(catDao.getCatById(catId).map { it.toCat() })
        } catch (e: Exception) {
            emit(Cat())
        }
    }
}
