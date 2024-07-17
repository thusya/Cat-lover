package com.thusee.core_database.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.thusee.core_database.model.CatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Query("SELECT * FROM cat_entity")
    fun getCats(): Flow<List<CatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCats(cats: List<CatEntity>)

    @Upsert
    suspend fun upsertCat(cat: CatEntity)

    @Upsert
    suspend fun upsertCats(cats: List<CatEntity>)
}