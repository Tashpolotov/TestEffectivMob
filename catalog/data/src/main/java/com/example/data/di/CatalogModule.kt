package com.example.data.di

import com.example.core_utils.SharedPref
import com.example.data.remote.CatalogApiService
import com.example.data.repository.CatalogRepositoryImpl
import com.example.data.room.AppDatabase
import com.example.data.room.ProductDao
import com.example.domain.repository.CatalogRepository
import com.example.domain.usecase.CatalogUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CatalogModule {

    @Provides
    fun provideCatalogApiService(retrofit: Retrofit): CatalogApiService {
        return retrofit.create(CatalogApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCatalogRepository(apiService: CatalogApiService)
            :CatalogRepository{
        return CatalogRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCatalogUseCase(repository: CatalogRepository)
            :CatalogUseCase{
        return CatalogUseCase(repository)
    }

}