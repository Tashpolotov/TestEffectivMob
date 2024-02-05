package com.example.core_utils.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.core_utils.Resource
import com.example.core_utils.showToast
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

abstract class BaseFragment(
    @LayoutRes layoutRes: Int,
) : Fragment(layoutRes) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupRequests()
        setupSubscribers()
        setupListeners()
        initSubscribers()
    }

    open fun initialize() {}

    open fun setupRequests() {}

    open fun setupSubscribers() {}

    open fun setupListeners() {}
    open fun initSubscribers() {}

    /**
     * for @
     */

    /**
     * For what
     *
     * @param state FSf
     * @param onSuccess fs
     */
    protected fun <T> StateFlow<Resource<T>>.collectUIState(
        state: ((Resource<T>) -> Unit)? = null,
        onSuccess: (data: T) -> Unit,
        onError: ((error: Resource.Error<T>) -> Unit)? = null
    ) {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@collectUIState.collect { resource ->
                    state?.invoke(resource)
                    when (resource) {
                        is Resource.Empty -> {
                            // Обработка Resource.Empty, если необходимо
                        }

                        is Resource.Error -> {
                            context?.showToast(resource.message)
                            onError?.invoke(resource) // Вызов блока onError в случае ошибки
                        }

                        is Resource.Loading -> {
                            // Обработка Resource.Loading, если необходимо
                        }

                        is Resource.Success -> {
                            resource.data?.let { onSuccess(it) }
                        }

                        else -> {
                            // Дополнительная обработка других типов Resource
                        }
                    }
                }
            }
        }
    }
}