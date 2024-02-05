package com.example.testeffectivmob

import com.example.data.room.ProductDao

interface DatabaseProvider {
    fun getProductDao(): ProductDao
}