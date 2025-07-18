package com.sysco.planetdetails.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sysco.planetdetails.R
import com.sysco.shared.core.presentation.image.NetworkImage

@Composable
fun PlanetDetailsContent(
    modifier: Modifier = Modifier,
    name: String,
    gravity: String,
    image: String,
    orbitalPeriod: String
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(name, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        NetworkImage(
            image = image
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            PlanetDetailInfoItem(
                label = stringResource(R.string.gravity),
                value = gravity
            )
            Spacer(modifier = Modifier.height(8.dp))
            PlanetDetailInfoItem(
                label = stringResource(R.string.orbital_period),
                value = orbitalPeriod
            )
        }
    }
}