package com.viniciusjanner.desafio.sicredi.presentation

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.viniciusjanner.desafio.sicredi.R
import com.viniciusjanner.desafio.sicredi.databinding.ActivityMainBinding
import com.viniciusjanner.desafio.sicredi.util.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        // Splash Screen
        initSplahsScreen()

        // Home Screen
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupNavigation()
    }

    private fun initSplahsScreen() {
        runBlocking {
            when (Build.VERSION.SDK_INT) {
                in AppConstants.sdkMin..AppConstants.sdkMax -> {
                    delay(AppConstants.delay)
                    setTheme(R.style.Theme_Home)
                }
                else -> {
                    installSplashScreen()
                    delay(AppConstants.delay)
                }
            }
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.EventListFragment,
            ),
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isTopLevelDestination = appBarConfiguration.topLevelDestinations.contains(destination.id)
            if (!isTopLevelDestination) {
                binding.toolbar.setNavigationIcon(R.drawable.ic_back)
            }
        }
    }
}
