package com.sysco.planets.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sysco.planets.R
import com.sysco.shared.core.presentation.image.NetworkImage

@Composable
fun PlanetListItem(
    modifier: Modifier = Modifier,
    name: String,
    image: String,
    climate: String,
    onClick: () -> Unit
) {
    LocalContext.current

    Row(
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                stringResource(R.string.climate, climate),
                style = MaterialTheme.typography.titleSmall
            )
        }
        NetworkImage(image)
    }
}