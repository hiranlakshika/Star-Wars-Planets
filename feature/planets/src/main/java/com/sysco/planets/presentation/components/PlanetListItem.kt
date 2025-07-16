package com.sysco.planets.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PlanetListItem(
    modifier: Modifier = Modifier,
    name: String,
    image: String,
    climate: String,
    onClick: () -> Unit
) {
    Row(modifier = modifier.clickable {
        onClick()
    }) {
        Column {
            Text(name)
            Text(climate)
        }
    }
}