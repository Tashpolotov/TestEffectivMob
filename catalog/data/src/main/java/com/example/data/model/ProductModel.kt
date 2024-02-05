package com.example.data.model

import java.io.Serializable

data class ProductModel(
    val id: String?,
    val title: String?,
    val subtitle: String?,
    val price: PriceModel?,
    val feedback: FeedbackModel?,
    val tags: List<String>? = null,
    val available: Int?,
    val description: String?,
    val info: List<InfoModel>? = null,
    val ingredients: String?
)
