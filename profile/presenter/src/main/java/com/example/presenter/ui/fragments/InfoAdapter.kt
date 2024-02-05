package com.example.presenter.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Product
import com.example.presenter.R
import com.example.presenter.databinding.ItemBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator


class InfoAdapter(val onClick: (Product) -> Unit, val deleteProductToDatabase: (Product) -> Unit) :
    ListAdapter<Product, InfoAdapter.InfoViewHolder>(ProductDiffCallback()) {

    private var imageClickListener: OnImageClickListener? = null

    init {
        imageClickListener = object : OnImageClickListener {
            override fun onImageClick(product: Product) {
                deleteProductToDatabase(product)
            }
        }
    }

    interface OnImageClickListener {
        fun onImageClick(product: Product)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflater, parent, false)
        return InfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    inner class InfoViewHolder(private val binding: ItemBinding)
        : RecyclerView.ViewHolder(binding.root) {


        fun bind(model: Product) {
            binding.tvDesc.text = model.subtitle
            binding.tvSale.text = "${model.price?.price} ${model.price?.unit}"
            binding.tvCena.text = "${model.price?.priceWithDiscount} ${model.price?.unit}"
            binding.tvName.text = model.title
            binding.tvDiscount.text = "${model.price?.discount} ${model.price?.unit}"
            binding.tvRating.text = model.feedback?.rating.toString()

            binding.imgHeart.setOnClickListener {
                imageClickListener?.onImageClick(model)
                deleteProductToDatabase(model)
                notifyDataSetChanged()
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

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}