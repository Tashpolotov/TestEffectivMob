package com.example.domain.repository

import com.example.core_utils.Resource
import com.example.domain.model.Product
import com.example.domain.model.ProductList
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {

    suspend fun getCatalog():Flow<Resource<ProductList>>


}