package com.sysco.starwarsplanets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sysco.main.presentation.MainViewModel
import com.sysco.starwarsplanets.navigation.NavigationRoot
import com.sysco.starwarsplanets.ui.theme.StarWarsPlanetsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.state.value.isLoading
            }
        }

        setContent {
            StarWarsPlanetsTheme {
                NavigationRoot()
            }
        }
    }
}