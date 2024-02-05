package com.example.presenter.ui.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.core_utils.loadImage
import com.example.presenter.databinding.ItemSliderImageBinding

class HotelSliderAdapter : ListAdapter<Int, HotelSliderAdapter.HotelSliderViewHolder>(
    DiffUtilItemCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelSliderViewHolder {
        return HotelSliderViewHolder(
            ItemSliderImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HotelSliderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HotelSliderViewHolder(private val binding: ItemSliderImageBinding) :
        ViewHolder(binding.root) {
        fun bind(hotels: Int) {
            binding.imgTovar.loadImage(hotels)
        }
    }

    private class DiffUtilItemCallback : DiffUtil.ItemCallback<Int>() {

        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
}