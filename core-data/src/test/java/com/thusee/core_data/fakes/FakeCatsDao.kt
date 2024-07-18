package com.thusee.core_data.fakes

import com.thusee.core_database.db.CatDao
import com.thusee.core_database.model.CatEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class FakeCatsDao(
    catList: List<CatEntity>
) : CatDao {
    private val _cats = catList.toMutableList()
    private val catSStream = MutableStateFlow(_cats.toList())

    override fun getCats(): Flow<List<CatEntity>> = catSStream

    override suspend fun upsertCat(cat: CatEntity) {
        _cats.removeIf { it.id == cat.id }
        _cats.add(cat)
        catSStream.emit(_cats)
    }

    override suspend fun upsertCats(cats: List<CatEntity>) {
        val newIds = cats.map { it.id }
        _cats.removeIf { newIds.contains(it.id) }
        _cats.addAll(cats)
        catSStream.emit(_cats)
    }

    override fun getCatById(catId: String): Flow<CatEntity> {
        return flow {
            val cat = _cats.find { it.id == catId }
            if (cat != null) {
                emit(cat)
            } else {
                throw NoSuchElementException("No cat found with id $catId")
            }
        }
    }
}