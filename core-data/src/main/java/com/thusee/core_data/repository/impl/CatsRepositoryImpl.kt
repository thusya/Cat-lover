package com.thusee.core_data.repository.impl

import com.thusee.core_data.model.Cat
import com.thusee.core_data.repository.CatsRepository
import com.thusee.core_data.utils.ResultOf
import com.thusee.core_database.db.CatDao
import com.thusee.core_database.model.CatEntity
import com.thusee.core_network.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val catDao: CatDao,
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

    suspend fun getCats(): ResultOf<List<Cat>> {
        try {
            val result = apiService.getImages(
//                "live_w60ZIiiWAolIxu1QZkKys86dIraYOfFrpddj8WowOrvdHro90LJtfIrV26bfj879",
                20,
                1
            )

            val cats = result
                .filter {
                    it.breeds.isNotEmpty()
                }
                .map {
                    Cat(
                        it.breeds[0].name,
                        it.url,
                        it.breeds[0].description
                    )
                }

            if (cats.isEmpty()) {
                return ResultOf.Failure(
                    Throwable("Empty Cats")
                )
            }

            return ResultOf.Success(cats)
        } catch (e: Exception) {
            return ResultOf.Failure(e)
        }
    }

    override suspend fun fetchCats(): ResultOf<Unit> {
        try {
            val catsResponse = apiService.getImages(
//                "live_w60ZIiiWAolIxu1QZkKys86dIraYOfFrpddj8WowOrvdHro90LJtfIrV26bfj879",
                20,
                1
            )
            if (catsResponse.isEmpty()) return ResultOf.Failure(Throwable("No cats found"))
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
            catDao.upsertCats(catModels)
            return ResultOf.Success(Unit)
        } catch (e: Exception) {
            return ResultOf.Failure(e)
        }
    }

    override suspend fun onFavoriteCat(cat: Cat): ResultOf<Unit> {
        val catModel = CatEntity(
            cat.id,
            cat.breed,
            cat.image,
            cat.description,
            !cat.isFavorite,
        )

        try {
            catDao.upsertCat(catModel)
            return ResultOf.Success(Unit)
        } catch (e: Exception) {
            return ResultOf.Failure(e)
        }
    }
}