package com.example.testeffectivmob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.core_utils.SharedPref
import com.example.testeffectivmob.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPref
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)

        val navView: BottomNavigationView = binding.navView

        val host =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = host.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
               com.example.presenter.R.id.nav_graph_registration, R.id.navigation_main, R.id.navigation_basket,
                R.id.navigation_catalog, R.id.navigation_profile, R.id.navigation_sale,
            )
        )
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == com.example.presenter.R.id.registerFragment ||
                destination.id == com.example.presenter.R.id.splashFragment


            ) {
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }
    }
}