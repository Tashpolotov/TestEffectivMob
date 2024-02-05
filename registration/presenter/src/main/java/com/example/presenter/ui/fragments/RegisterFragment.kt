package com.example.presenter.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core_utils.SharedPref
import com.example.core_utils.base.BaseFragment
import com.example.core_utils.getTextWatcherForName
import com.example.core_utils.getTextWatcherForPhoneNumber
import com.example.core_utils.validateName
import com.example.core_utils.validatePhoneNumber
import com.example.presenter.R
import com.example.presenter.databinding.FragmentRegisterBinding



class RegisterFragment : BaseFragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private lateinit var sharedPref: SharedPref

    override fun initialize() {
        sharedPref = SharedPref(requireContext())
        binding.etNumber.setText("+7")
        val nameWatcher = getTextWatcherForName(binding.etName, binding.inputEtName)
        val secondNameWatcher = getTextWatcherForName(binding.etSecondName, binding.inputEtSecondName)
        val passwordWatcher = getTextWatcherForPhoneNumber(binding.etNumber, binding.inputEtNumber)

        binding.etName.addTextChangedListener(nameWatcher)
        binding.etSecondName.addTextChangedListener(secondNameWatcher)
        binding.etNumber.addTextChangedListener(passwordWatcher)


        binding.btnGo.setOnClickListener {

            if (validateName(binding.etName, binding.inputEtName) &&
                validateName(binding.etSecondName, binding.inputEtSecondName) &&
                validatePhoneNumber(binding.etNumber, binding.inputEtNumber)
            ) {
                sharedPref.savedName = binding.etName.text.toString()
                sharedPref.savedSecondName = binding.etSecondName.text.toString()
                sharedPref.savedNumber = binding.etNumber.text.toString()
                sharedPref.isSaveNameUser = true
                val uri = "example.feature://main".toUri()
                val request = NavDeepLinkRequest.Builder
                    .fromUri(uri)
                    .build()
                findNavController().navigate(request)
            } else {
                sharedPref.isSaveNameUser = false
            }
        }
    }
}