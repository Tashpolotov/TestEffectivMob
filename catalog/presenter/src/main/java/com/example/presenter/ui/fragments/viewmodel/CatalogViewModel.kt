package com.example.presenter.ui.fragments.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.core_utils.Resource
import com.example.core_utils.base.BaseViewModel
import com.example.domain.model.Product
import com.example.domain.model.ProductList
import com.example.domain.usecase.CatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(private val useCase: CatalogUseCase):BaseViewModel() {

    private val _catalog = MutableStateFlow<Resource<ProductList>>(Resource.Empty())
    val catalog = _catalog.asStateFlow()


    fun loadCatalog(){
        viewModelScope.launch {

            useCase.getCatalog().collectData(_catalog)
        }
    }

}