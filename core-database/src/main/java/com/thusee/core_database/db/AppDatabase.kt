package com.thusee.core_database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thusee.core_database.model.CatEntity

@Database(entities = [CatEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCatDao(): CatDao
}