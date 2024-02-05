package com.example.data.room

import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.model.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertProduct(product: Product)

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<Product>

    @Query("DELETE FROM products WHERE id = :productId")
    fun deleteProductById(productId: String)

    @Query("SELECT * FROM products WHERE id = :productId LIMIT 1")
    fun getProductById(productId: String): Product?

    @Query("DELETE FROM products")
    fun getAllDelete()
}
