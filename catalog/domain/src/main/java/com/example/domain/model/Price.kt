package com.example.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Price(

    val price: String?,
    val discount: Int?,
    val priceWithDiscount: String?,
    val unit: String?
)
