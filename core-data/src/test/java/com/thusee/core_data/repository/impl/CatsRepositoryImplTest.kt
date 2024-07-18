package com.thusee.core_data.repository.impl

import com.thusee.core_data.async.AsyncOperation
import com.thusee.core_data.async.CatRepoAsyncEvents
import com.thusee.core_data.fakes.FakeCatApiService
import com.thusee.core_data.fakes.FakeCatsDao
import com.thusee.core_data.fakes.fakeCatResponse
import com.thusee.core_data.model.Cat
import com.thusee.core_data.utils.toCat
import com.thusee.core_data.utils.toCatEntity
import com.thusee.core_database.model.CatEntity
import com.thusee.core_network.model.CatResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CatsRepositoryImplTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val fakeCatApi = FakeCatApiService(fakeCatResponse, 2000L, testScope)
    private val fakeCatModels = fakeCatResponse.map {
        CatEntity(
            id = it.id,
            name = it.breeds.getOrNull(0)?.name ?: "",
            imageUrl = it.url,
            description = it.breeds.getOrNull(0)?.description,
            isFavorite = false,
            origin = it.breeds.getOrNull(0)?.origin,
            lifeSpan = it.breeds.getOrNull(0)?.life_span,
            temperament = it.breeds.getOrNull(0)?.temperament,
            wikipediaUrl = it.breeds.getOrNull(0)?.wikipedia_url
        )
    }

    private val fakeCatsDao = FakeCatsDao(fakeCatModels)

    private val catsRepository = CatsRepositoryImpl(
        fakeCatApi,
        fakeCatsDao,
        testDispatcher,
        testScope
    )

    @Test
    fun cats_emitDbData() = testScope.runTest {
        val cats = catsRepository.cats.first()
        assertEquals(cats, fakeCatModels.map { it.toCat() })
    }

    @Test
    fun favoriteAction_dataUpdated() = testScope.runTest {
        val someRandomCat = catsRepository.cats.first()[0]

        catsRepository.onFavoriteCat(someRandomCat)

        val catAfterFavoriteAction = catsRepository.cats.first().find { it.id == someRandomCat.id }

        assertNotNull(catAfterFavoriteAction)
        assertTrue(someRandomCat.isFavorite != catAfterFavoriteAction?.isFavorite)
    }

    @Test
    fun favoriteAction_asyncStatusUpdated() = testScope.runTest {
        val catBeforeFavoriteAction = catsRepository.cats.first()[0]

        val values = mutableListOf<AsyncOperation<CatRepoAsyncEvents>>()
        backgroundScope.launch(testDispatcher) {
            catsRepository.operationStatus.toList(values)
        }

        catsRepository.onFavoriteCat(catBeforeFavoriteAction)

        assertTrue(values.first() is AsyncOperation.Loading)
        assertTrue(values.last() is AsyncOperation.Success)
    }

    @Test
    fun fetchCat_updateFromNetwork_repoUpdated() = testScope.runTest {
        val initialItems = emptyList<CatEntity>()
        val fakeCatsDao = FakeCatsDao(initialItems)

        val listFromApi = fakeCatResponse
        val fakeCatApi = FakeCatApiService(listFromApi, 2000L, testScope)

        val catsRepository = CatsRepositoryImpl(
            fakeCatApi,
            fakeCatsDao,
            testDispatcher,
            testScope
        )

        val repoResults = mutableListOf<List<Cat>>()
        backgroundScope.launch(testDispatcher) {
            catsRepository.cats.toList(repoResults)
        }

        catsRepository.fetchCats()
        assertEquals(repoResults[0], initialItems)
        assertEquals(repoResults.last(), listFromApi.map { it.toCatEntity().toCat() })
    }

    @Test
    fun fetchCat_updateFromNetwork_asyncStatusChanged() = testScope.runTest {
        val initialItems = emptyList<CatEntity>()
        val fakeCatsDao = FakeCatsDao(initialItems)

        val listFromApi = fakeCatResponse
        val fakeCatApi = FakeCatApiService(listFromApi, 2000L, testScope)

        val catsRepository = CatsRepositoryImpl(
            fakeCatApi,
            fakeCatsDao,
            testDispatcher,
            testScope
        )

        val statusResults = mutableListOf<AsyncOperation<CatRepoAsyncEvents>>()
        backgroundScope.launch(testDispatcher) {
            catsRepository.operationStatus.toList(statusResults)
        }
        catsRepository.fetchCats()

        assertEquals(statusResults.first(), AsyncOperation.Loading)
        assertEquals(
            statusResults.last(),
            AsyncOperation.Success(CatRepoAsyncEvents.LoadCatsSuccess)
        )
    }

    @Test
    fun fetchCat_emptyCatsReturned_errorStateCreated() = testScope.runTest {
        val initialItems = emptyList<CatEntity>()
        val fakeCatsDao = FakeCatsDao(initialItems)

        val listFromApi = emptyList<CatResponse>()
        val fakeCatApi = FakeCatApiService(listFromApi, 2000L, testScope)

        val catsRepository = CatsRepositoryImpl(
            fakeCatApi,
            fakeCatsDao,
            testDispatcher,
            testScope
        )

        val statusResults = mutableListOf<AsyncOperation<CatRepoAsyncEvents>>()
        backgroundScope.launch(testDispatcher) {
            catsRepository.operationStatus.toList(statusResults)
        }
        catsRepository.fetchCats()

        assertEquals(statusResults.first(), AsyncOperation.Loading)
        assertTrue(statusResults.last() is AsyncOperation.Failure)
    }
}