package com.example.presenter.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core_utils.SharedPref
import com.example.core_utils.base.BaseFragment
import com.example.data.room.AppDatabase
import com.example.data.room.ProductDao
import com.example.domain.model.Product
import com.example.presenter.R
import com.example.presenter.databinding.FragmentCatalogBinding
import com.example.presenter.ui.fragments.adapter.CatalogAdapter
import com.example.presenter.ui.fragments.viewmodel.CatalogViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.addHeaderLenient

@AndroidEntryPoint
class CatalogFragment : BaseFragment(R.layout.fragment_catalog) {

    private val binding by viewBinding(FragmentCatalogBinding::bind)
    private val viewModel by viewModels<CatalogViewModel>()
    private lateinit var adapter: CatalogAdapter
    private lateinit var productDao: ProductDao
    private lateinit var appDatabase: AppDatabase
    private lateinit var sharedPref:SharedPref

    private fun onClick(model: Product) {
        val bundle = Bundle().apply {
            putSerializable("product", model)
        }
        findNavController().navigate(R.id.action_catalogFragment_to_detailFragment, bundle)
    }


    override fun initialize() {
        sharedPref = SharedPref(requireContext())
        binding.imgFavStrelka.setOnClickListener {
            showPopupMenu(it)
        }


        appDatabase = AppDatabase.getInstance(requireContext())
        productDao = appDatabase.productDao()

        adapter = CatalogAdapter(this::onClick, this::saveProductToDatabase, sharedPref, productDao)
        binding.rv.adapter = adapter
        resetBackgrounds()
        binding.imgAllSeeX.visibility = View.VISIBLE
        binding.linearAllSee.background = getDrawable(R.drawable.shape_linear2)
    }

    override fun initSubscribers() {

        viewModel.catalog.collectUIState(
            state = {
                binding.progressBar.isVisible = true
            },
            onSuccess = {product ->
                binding.progressBar.isVisible = false
                val selectedSuntanTag = "suntan"
                val selectedMaskTag = "mask"
                val selectedBodyTag = "body"
                val selectedFaceTag = "face"

                adapter.submitList(product.items)

                binding.linearAllSee.setOnClickListener {
                    resetBackgrounds()
                    binding.imgAllSeeX.visibility = View.VISIBLE
                    it.background = getDrawable(R.drawable.shape_linear2)
                    adapter.submitList(product.items)
                    binding.imgAllSeeX.setOnClickListener {

                    }
                }

                binding.linearAllFace.setOnClickListener {
                    resetBackgrounds()
                    binding.imgAllFaceX.visibility = View.VISIBLE
                    it.background = getDrawable(R.drawable.shape_linear2)
                    val filteredItems = product.items.filter { product ->
                        product.tags!!.contains(selectedFaceTag)
                    }
                    adapter.submitList(filteredItems)
                    binding.imgAllFaceX.setOnClickListener {
                        resetBackgrounds()
                        adapter.submitList(product.items)
                        binding.imgAllSeeX.visibility = View.VISIBLE
                        binding.linearAllSee.background = getDrawable(R.drawable.shape_linear2)
                    }
                }

                binding.linearAllMask.setOnClickListener {
                    resetBackgrounds()
                    binding.imgAllMaskX.visibility = View.VISIBLE

                    it.background = getDrawable(R.drawable.shape_linear2)
                    val selectedMaskTag = product.items.filter { product ->
                        product.tags!!.contains(selectedMaskTag)
                    }
                    adapter.submitList(selectedMaskTag)
                    binding.imgAllMaskX.setOnClickListener {
                        resetBackgrounds()
                        adapter.submitList(product.items)
                        binding.imgAllSeeX.visibility = View.VISIBLE
                        binding.linearAllSee.background = getDrawable(R.drawable.shape_linear2)
                    }
                }

                binding.linearAllBody.setOnClickListener {
                    resetBackgrounds()
                    binding.imgAllBodyX.visibility = View.VISIBLE

                    it.background = getDrawable(R.drawable.shape_linear2)
                    val selectedBodyTag = product.items.filter { product ->
                        product.tags!!.contains(selectedBodyTag)
                    }
                    adapter.submitList(selectedBodyTag)
                    binding.imgAllBodyX.setOnClickListener {
                        resetBackgrounds()
                        adapter.submitList(product.items)
                        binding.imgAllSeeX.visibility = View.VISIBLE
                        binding.linearAllSee.background = getDrawable(R.drawable.shape_linear2)
                    }
                }

                binding.linearAllZagar.setOnClickListener {
                    resetBackgrounds()
                    binding.imgAllZagarX.visibility = View.VISIBLE

                    it.background = getDrawable(R.drawable.shape_linear2)
                    val selectedSuntanTag = product.items.filter { product ->
                        product.tags!!.contains(selectedSuntanTag)
                    }
                    adapter.submitList(selectedSuntanTag)
                    binding.imgAllZagarX.setOnClickListener {
                        resetBackgrounds()
                        adapter.submitList(product.items)
                        binding.imgAllSeeX.visibility = View.VISIBLE
                        binding.linearAllSee.background = getDrawable(R.drawable.shape_linear2)
                    }
                }
            }
        )
        viewModel.loadCatalog()
    }

    private fun resetBackgrounds() {
        binding.imgAllSeeX.visibility = View.GONE
        binding.linearAllSee.background = getDrawable(R.drawable.shape_linear)
        binding.imgAllFaceX.visibility = View.GONE
        binding.linearAllFace.background = getDrawable(R.drawable.shape_linear)
        binding.imgAllMaskX.visibility = View.GONE
        binding.linearAllMask.background = getDrawable(R.drawable.shape_linear)
        binding.imgAllBodyX.visibility = View.GONE
        binding.linearAllBody.background = getDrawable(R.drawable.shape_linear)
        binding.imgAllZagarX.visibility = View.GONE
        binding.linearAllZagar.background = getDrawable(R.drawable.shape_linear)
    }
    private fun getDrawable(resourceId: Int): Drawable? {
        return ContextCompat.getDrawable(requireContext(), resourceId)
    }

    private fun showPopupMenu(v: View) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(R.menu.poppup_menu, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.popular -> {
                    viewModel.catalog.collectUIState(
                        state = {
                            binding.progressBar.isVisible = true
                        },
                        onSuccess = { product ->
                            binding.progressBar.isVisible = false
                            val sortedList = product.items.sortedByDescending { it.feedback?.rating ?: 0.0 }
                            adapter.submitList(sortedList)
                        }
                    )
                    true
                }
                R.id.pricePlus -> {
                    viewModel.catalog.collectUIState(
                        state = {
                            binding.progressBar.isVisible = true
                        },
                        onSuccess = { product ->
                            binding.progressBar.isVisible = false
                            val sortedList = product.items.sortedBy { it.price?.priceWithDiscount?.toDoubleOrNull() ?: 0.0 }

                            adapter.submitList(sortedList)
                        }
                    )
                    true
                }

                R.id.priceMinus -> {
                    viewModel.catalog.collectUIState(
                        state = {
                            binding.progressBar.isVisible = true
                        },
                        onSuccess = { product ->
                            binding.progressBar.isVisible = false
                            val sortedList = product.items.sortedByDescending { it.price?.priceWithDiscount?.toDoubleOrNull() ?: 0.0 }

                            adapter.submitList(sortedList)
                        }
                    )
                    true
                }

                else -> false
            }
        }

        popup.show()
        }

    private fun saveProductToDatabase(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            productDao.insertProduct(product)
        }

    }
    }

