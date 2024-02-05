package com.example.presenter.ui.fragments

import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core_utils.SharedPref
import com.example.core_utils.base.BaseFragment
import com.example.data.room.AppDatabase
import com.example.data.room.ProductDao
import com.example.domain.model.Product
import com.example.presenter.R
import com.example.presenter.databinding.FragmentDetailBinding
import com.example.presenter.ui.fragments.adapter.HotelSliderAdapter
import com.example.presenter.ui.fragments.viewmodel.CatalogViewModel
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private var isDescriptionVisible = true
    private var isSostavVisible = false
    private lateinit var appDatabase: AppDatabase
    private lateinit var productDao: ProductDao



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



    override fun initialize() {

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val product = arguments?.getSerializable("product") as? Product

        // Проверка на null для безопасного извлечения аргументов
        if (product != null) {
            setupViews(product)
            setupListener()
            updateSostavVisibility()
            updateDescriptionVisibility()
        } else {
            Log.e("DetailFragment", "Product is null")
        }
        appDatabase = AppDatabase.getInstance(requireContext())
        productDao = appDatabase.productDao()
        lifecycleScope.launch {
            val imgHeart = isProductSavedInDatabase(product!!.id)
            if (imgHeart) {
                binding.imgClickHeart.visibility = View.VISIBLE
                binding.imgHeart.visibility = View.INVISIBLE
            } else {
                binding.imgClickHeart.visibility = View.INVISIBLE
                binding.imgHeart.visibility = View.VISIBLE
            }
        }

        val adapter = HotelSliderAdapter()
        binding.viewPager2.adapter = adapter

        val indicator: DotsIndicator = binding.indic
        indicator.setViewPager2(binding.viewPager2)
        val imageList = imageMap[product?.id] ?: emptyList()
        adapter.submitList(imageList)

    }

    private suspend fun isProductSavedInDatabase(productId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val product = productDao.getProductById(productId)
            product != null
        }
    }

    private fun setupViews(product: Product) {
        // Улучшенный способ форматирования строк для доступных товаров
        val formattedAvailable = when (val available = product.available) {
            null, 0 -> "Недоступно"
            else -> {
                val suffix = when {
                    available % 10 == 1 && available % 100 != 11 -> "штука"
                    available % 10 in 2..4 && (available % 100 < 10 || available % 100 >= 20) -> "штуки"
                    else -> "штук"
                }
                "Доступно для заказа $available $suffix"
            }
        }

        binding.apply {
            ratingBar.rating = product.feedback?.rating?.toFloat() ?: 0.0f
            tvAvilable.text = formattedAvailable
            tvIngridients.text = product.ingredients
            tvDiscount.text = "-${product.price?.discount}%"
            tvRating.text = product.feedback?.rating?.toString() ?: "0.0"
            tvCount.text = "${product.feedback?.count} отзыва"
            tvTitle.text = product.title
            tvTitle2.text = product.title
            tvTitle1.text = product.info?.getOrNull(0)?.title
            tvTitle2Art2.text = product.info?.getOrNull(1)?.title
            tvTitle3.text = product.info?.getOrNull(2)?.title
            tvSubtitle.text = product.subtitle
            tvPrice.text = "${product.price?.price} ${product.price?.unit}"
            binding.tvPrice.paintFlags = binding.tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.tvPrice1.paintFlags = binding.tvPrice1.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            tvPrice1.text = "${product.price?.price} ${product.price?.unit}"
            tvPriceWithDiscount.text = "${product.price?.priceWithDiscount} ${product.price?.unit}"
            tvPriceWithDiscount1.text = "${product.price?.priceWithDiscount} ${product.price?.unit}"
            tvDesc.text = product.description
            tvValue1.text = product.info?.getOrNull(0)?.value
            tvValue2.text = product.info?.getOrNull(1)?.value
            tvValue3.text = product.info?.getOrNull(2)?.value
        }
    }

    private fun setupListener() {
        binding.tvInvisible.setOnClickListener {
            isDescriptionVisible = false
            updateDescriptionVisibility()
        }

        binding.tvVisible.setOnClickListener {
            isDescriptionVisible = true
            updateDescriptionVisibility()
        }

        binding.tvVisibleSostav.setOnClickListener {
            isSostavVisible = true
            updateSostavVisibility()
        }

        binding.tvInvisibleSostav.setOnClickListener {
            isSostavVisible = false
            updateSostavVisibility()
        }
    }

    private fun updateDescriptionVisibility() {
        val descriptionVisibility = if (isDescriptionVisible) View.VISIBLE else View.GONE

        binding.apply {
            tvDesc.visibility = descriptionVisibility
            tvInvisible.visibility = if (isDescriptionVisible) View.VISIBLE else View.GONE
            tvVisible.visibility = if (!isDescriptionVisible) View.VISIBLE else View.GONE
        }
    }

    private fun updateSostavVisibility() {
        val maxLines = if (isSostavVisible) Int.MAX_VALUE else 2
        val ellipsize = if (isSostavVisible) null else TextUtils.TruncateAt.END

        binding.apply {
            tvIngridients.maxLines = maxLines
            tvIngridients.ellipsize = ellipsize
            tvVisibleSostav.visibility = if (isSostavVisible) View.GONE else View.VISIBLE
            tvInvisibleSostav.visibility = if (isSostavVisible) View.VISIBLE else View.GONE
        }
    }
}