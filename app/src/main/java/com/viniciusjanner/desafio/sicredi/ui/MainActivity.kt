package com.viniciusjanner.desafio.sicredi.ui

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.viniciusjanner.desafio.sicredi.R
import com.viniciusjanner.desafio.sicredi.databinding.ActivityMainBinding
import com.viniciusjanner.desafio.sicredi.util.AppConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        // Splash Screen
        initSplahsScreen()

        // Home Screen
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.activity_main)
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
}
