package com.thusee.core_data.di

import com.thusee.core_data.repository.CatsRepository
import com.thusee.core_data.repository.impl.CatsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CatRepositoryModule {

    @Singleton
    @Binds
    fun bindCatRepository(
        catsRepositoryImpl: CatsRepositoryImpl
    ): CatsRepository
}