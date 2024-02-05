package com.example.presenter.ui.fragments.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core_utils.SharedPref
import com.example.data.room.AppDatabase
import com.example.data.room.ProductDao
import com.example.domain.model.Product
import com.example.presenter.R
import com.example.presenter.databinding.ItemCatalogBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class CatalogAdapter(val onClick: (Product) -> Unit, val saveProductToDatabase: (Product) -> Unit,
                     private val sharedPref: SharedPref,
                     private val productDao: ProductDao
) :
    ListAdapter<Product, CatalogAdapter.CatalogViewHolder>(CatalogDiff()) {


    private var imageClickListener: OnImageClickListener? = null

    init {
        imageClickListener = object : OnImageClickListener {
            override fun onImageClick(product: Product) {
                saveProductToDatabase(product)

            }
        }
    }

    interface OnImageClickListener {
        fun onImageClick(product: Product)
    }

    inner class CatalogViewHolder(private val binding: ItemCatalogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Product) {

            val isHeartVisible = sharedPref.getHeartVisibility(model.id)
            binding.imgHeart.visibility = if (isHeartVisible) View.INVISIBLE else View.VISIBLE
            binding.imgClickHeart.visibility = if (isHeartVisible) View.VISIBLE else View.INVISIBLE
            binding.tvDesc.text = model.subtitle
            //для зачеркивания
            binding.tvSale.text = "${model.price?.price} ${model.price?.unit}"
            binding.tvSale.paintFlags = binding.tvSale.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            binding.tvCena.text = "${model.price?.priceWithDiscount} ${model.price?.unit}"
            binding.tvName.text = model.title
            binding.tvDiscount.text = "-${model.price?.discount}%"
            binding.tvRating.text = model.feedback?.rating.toString()

            val isProductSaved = isProductSavedInDatabase(model.id)

            // Установка видимости изображений сердца в соответствии с наличием продукта в базе данных
            binding.imgHeart.visibility = if (isProductSaved) View.INVISIBLE else View.VISIBLE
            binding.imgClickHeart.visibility = if (isProductSaved) View.VISIBLE else View.INVISIBLE

            binding.imgHeart.setOnClickListener {
                imageClickListener?.onImageClick(model)

                // Сохранение продукта в базе данных Room
                saveProductToDatabase(model)

                // Сохранение состояния видимости изображений сердца в SharedPreferences
                sharedPref.saveHeartVisibility(model.id, true)

                // Обновление видимости изображений сердца
                binding.imgHeart.visibility = View.INVISIBLE
                binding.imgClickHeart.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                onClick(model)
            }

             val imageMap = mapOf(
                "cbf0c984-7c6c-4ada-82da-e29dc698bb50" to listOf(
                    R.drawable.img_tovar6,
                    R.drawable.img_tovar5
                ),
                "54a876a5-2205-48ba-9498-cfecff4baa6e" to listOf(
                    R.drawable.img_tovar,
                    R.drawable.img_tovar2
                ),
                "75c84407-52e1-4cce-a73a-ff2d3ac031b3" to listOf(
                    R.drawable.img_tovar5,
                    R.drawable.img_tovar6
                ),
                "16f88865-ae74-4b7c-9d85-b68334bb97db" to listOf(
                    R.drawable.img_tovar3,
                    R.drawable.img_tovar4
                ),
                 // Добавленные айдишники и изображения
                 "7b88e236-88c3-4e5d-b5d3-40de8a6a6df1" to listOf(
                     R.drawable.img_tovar2,
                     R.drawable.img_tovar6
                 ),
                 "a825add1-7a9a-40b0-8de8-8e4328d65db6" to listOf(
                     R.drawable.img_tovar4,
                     R.drawable.img_tovar5
                 ),
                 "26f88856-ae74-4b7c-9d85-b68334bb97db" to listOf(
                     R.drawable.img_tovar2,
                     R.drawable.img_tovar3
                 ),

                 "15f88865-ae74-4b7c-9d81-b78334bb97db" to listOf(
                     R.drawable.img_tovar6,
                     R.drawable.img_tovar
                 ),
                 "88f88865-ae74-4b7c-9d81-b78334bb97db" to listOf(
                     R.drawable.img_tovar4,
                     R.drawable.img_tovar3
                 ),

                 "55f58865-ae74-4b7c-9d81-b78334bb97db" to listOf(
                     R.drawable.img_tovar,
                     R.drawable.img_tovar5
                 ),

            )

            val adapter = HotelSliderAdapter()
            binding.viewPager2.adapter = adapter

            val indicator: DotsIndicator = binding.indic
            indicator.setViewPager2(binding.viewPager2)
            val imageList = imageMap[model.id] ?: emptyList()
            adapter.submitList(imageList)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        return CatalogViewHolder(ItemCatalogBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    private fun isProductSavedInDatabase(productId: String): Boolean {
        return runBlocking {
            withContext(Dispatchers.IO) {
                val product = productDao.getProductById(productId)

                // Если продукт найден, вернуть true, иначе false
                return@withContext product != null
            }
        }
}

class CatalogDiff : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
}
