package com.sysco.common.presentation.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
private fun LoadingIndicator() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(24.dp))
    }
}

@Composable
fun NetworkImage(image: String, isCached: Boolean = true) {
    val context = LocalContext.current
    SubcomposeAsyncImage(
        model = if (isCached) image else noCacheImageRequest(context, image),
        contentDescription = null,
    ) {
        val state = painter.state
        when (state) {
            AsyncImagePainter.State.Empty -> {
                LoadingIndicator()
            }

            is AsyncImagePainter.State.Error -> {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            is AsyncImagePainter.State.Loading -> {
                LoadingIndicator()
            }

            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent()
            }
        }
    }
}