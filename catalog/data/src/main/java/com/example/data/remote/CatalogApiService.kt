package com.example.data.remote

import com.example.data.model.ProductListModel
import com.example.data.model.ProductModel
import retrofit2.http.GET

interface CatalogApiService {

    @GET("97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getCatalog():ProductListModel
}