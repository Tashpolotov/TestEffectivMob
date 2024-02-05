package com.example.data.repository

import com.example.core_utils.Resource
import com.example.core_utils.base.BaseRepository
import com.example.data.mapper.toProduct
import com.example.data.mapper.toProductList
import com.example.data.remote.CatalogApiService
import com.example.domain.repository.CatalogRepository


class CatalogRepositoryImpl(
    private val apiService: CatalogApiService,
):BaseRepository(), CatalogRepository {
    override suspend fun getCatalog() = doRequest {

        apiService.getCatalog().toProductList()


    }
}