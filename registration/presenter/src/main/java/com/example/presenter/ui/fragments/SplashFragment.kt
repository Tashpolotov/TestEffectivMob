package com.example.presenter.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.presenter.R
import com.example.presenter.databinding.FragmentSplashBinding


class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)
    private lateinit var sharedPref: SharedPref


    override fun initialize() {
        sharedPref = SharedPref(requireContext())

        Handler(Looper.getMainLooper()).postDelayed({
            if(sharedPref.isSaveNameUser){
                val uri = "example.feature://catalog".toUri()
                val request = NavDeepLinkRequest.Builder
                    .fromUri(uri)
                    .build()
                findNavController().navigate(request)
            } else{
                findNavController().navigate(R.id.action_splashFragment_to_registerFragment)
            }

        }, 3000)

    }

}