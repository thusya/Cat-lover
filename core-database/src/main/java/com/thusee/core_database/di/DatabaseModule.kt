package com.thusee.core_database.di

import android.content.Context
import androidx.room.Room
import com.thusee.core_database.db.AppDatabase
import com.thusee.core_database.db.CatDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideCatDao(appDatabase: AppDatabase): CatDao {
        return appDatabase.getCatDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "cat_db.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}