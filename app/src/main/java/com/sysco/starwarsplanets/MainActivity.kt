package com.sysco.starwarsplanets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sysco.starwarsplanets.navigation.NavigationRoot
import com.sysco.starwarsplanets.ui.theme.StarWarsPlanetsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            StarWarsPlanetsTheme {
                NavigationRoot()
            }
        }
    }
}