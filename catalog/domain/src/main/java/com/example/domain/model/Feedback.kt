package com.example.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Feedback(
    val count: Int?,
    val rating: Double?
)
