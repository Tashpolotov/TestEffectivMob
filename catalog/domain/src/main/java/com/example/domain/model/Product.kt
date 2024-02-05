package com.example.domain.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "subtitle")
    val subtitle: String?,
    @ColumnInfo(name = "price")
    val price: Price?,
    @ColumnInfo(name = "feedback")

    val feedback: Feedback?,
    @ColumnInfo(name = "tags")

    val tags: List<String>?,
    @ColumnInfo(name = "available")
    val available: Int?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "info")
    val info: List<Info>?,
    @ColumnInfo(name = "ingredients")
    val ingredients: String?
):Serializable