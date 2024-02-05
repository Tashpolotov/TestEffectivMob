package com.example.presenter.ui.fragments

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core_utils.base.BaseFragment
import com.example.data.room.AppDatabase
import com.example.domain.model.Product
import com.example.presenter.R
import com.example.presenter.databinding.FragmentFavouriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteFragment : BaseFragment(R.layout.fragment_favourite) {

    private val binding by viewBinding(FragmentFavouriteBinding::bind)

    @Inject
    lateinit var appDatabase: AppDatabase
    private lateinit var adapter: InfoAdapter


    override fun initialize() {
        adapter = InfoAdapter(this::onClick, this::deleteProductToDatabase)
        binding.rv.adapter = adapter
        loadData()
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onClick(model: Product) {

    }


    private fun loadData() {
        lifecycleScope.launch {
            val products = withContext(Dispatchers.IO) {
                val database = AppDatabase.getInstance(requireContext())
                val productDao = database.productDao()
                productDao.getAllProducts()
            }

            Log.d("YourActivity", "All products: $products")
            adapter.submitList(products)
        }
    }

    private fun deleteProductToDatabase(product: Product) {
        lifecycleScope.launch(Dispatchers.IO) {
            val database = AppDatabase.getInstance(requireContext())
            val productDao = database.productDao()
            productDao.deleteProductById(product.id)

            val updatedProducts = productDao.getAllProducts()

            withContext(Dispatchers.Main) {
                adapter.submitList(updatedProducts)
            }
        }
    }
}