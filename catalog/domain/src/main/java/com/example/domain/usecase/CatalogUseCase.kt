package com.example.domain.usecase

import com.example.domain.repository.CatalogRepository

class CatalogUseCase(private val repository: CatalogRepository) {

    suspend fun getCatalog() = repository.getCatalog()


}