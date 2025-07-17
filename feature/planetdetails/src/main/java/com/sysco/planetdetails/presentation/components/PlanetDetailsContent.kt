package com.sysco.planetdetails.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.sysco.shared.core.domain.Constants
import com.sysco.shared.core.presentation.image.NetworkImage

@Composable
fun PlanetDetailsContent(
    modifier: Modifier = Modifier,
    gravity: String,
    orbitalPeriod: String
) {
    val scrollState = rememberScrollState()
    val containerSize = LocalWindowInfo.current.containerSize

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(gravity, style = MaterialTheme.typography.titleLarge)
        Text(
            orbitalPeriod,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        NetworkImage(
            isCached = false,
            image = Constants.DYNAMIC_LARGE_IMAGE_URL,
            size = (containerSize.height * 0.25f).dp
        )
    }
}