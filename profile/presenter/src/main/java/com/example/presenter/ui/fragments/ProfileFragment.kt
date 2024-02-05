package com.example.presenter.ui.fragments

import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core_utils.SharedPref
import com.example.core_utils.base.BaseFragment
import com.example.data.room.AppDatabase
import com.example.data.room.ProductDao
import com.example.presenter.R
import com.example.presenter.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private lateinit var sharedPref: SharedPref
    private lateinit var appDatabase: AppDatabase
    private lateinit var dao1: ProductDao

    override fun initialize() {
        appDatabase = AppDatabase.getInstance(requireContext())
        dao1 = appDatabase.productDao()
        sharedPref = SharedPref(requireContext())
        binding.tvNameUser.text = "${sharedPref.savedName} ${sharedPref.savedSecondName}"
        binding.tvNumberUser.text = sharedPref.savedNumber
        load()
        binding.constFavourite.setOnClickListener {
            findNavController().navigate(com.example.presenter.R.id.action_profileFragment_to_favouriteFragment)
        }
        binding.tvLogOut.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    dao1.getAllDelete()
                    sharedPref.deleteUserInfo()
                }

                withContext(Dispatchers.Main) {
                    val uri = "example.feature://register".toUri()
                    val request = NavDeepLinkRequest.Builder
                        .fromUri(uri)
                        .build()
                    findNavController().navigate(request)
                }
            }
        }

    }

    private fun getCorrectForm(number: Int): String {
        return when {
            number == 0 -> ""
            number % 100 in 11..19 -> "$number товаров"
            number % 10 == 1 -> "$number товар"
            number % 10 in 2..4 -> "$number товара"
            else -> "$number товаров"
        }
    }

    private fun load() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val products = dao1.getAllProducts()
                val numberOfProducts = products.size
                withContext(Dispatchers.Main) {
                    binding.tvTovar.text = getCorrectForm(numberOfProducts)
                }
            }
        }
    }
}
