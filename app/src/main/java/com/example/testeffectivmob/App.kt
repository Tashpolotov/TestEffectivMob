package com.example.testeffectivmob

import android.app.Application
import com.example.data.room.AppDatabase
import com.example.data.room.ProductDao
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application(), DatabaseProvider {

    override fun getProductDao(): ProductDao {
        return AppDatabase.getInstance(this).productDao()
    }
}