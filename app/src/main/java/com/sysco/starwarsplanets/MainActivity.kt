package com.sysco.starwarsplanets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sysco.starwarsplanets.navigation.NavigationRoot
import com.sysco.starwarsplanets.ui.theme.StarWarsPlanetsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StarWarsPlanetsTheme {
                NavigationRoot()
            }
        }
    }
}