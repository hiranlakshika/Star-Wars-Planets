package com.sysco.shared.core.presentation.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
private fun LoadingIndicator(size: Dp = 48.dp) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(size)) {
        CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(24.dp))
    }
}

@Composable
fun NetworkImage(
    image: String,
    isCached: Boolean = true,
    conerRadius: Dp = 12.dp
) {
    val context = LocalContext.current
    SubcomposeAsyncImage(
        model = if (isCached) image else noCacheImageRequest(context, image),
        contentDescription = null,
    ) {
        val state = painter.state
        when (state) {
            is AsyncImagePainter.State.Empty, is AsyncImagePainter.State.Loading -> {
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

            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent(
                    modifier = Modifier
                        .clip(RoundedCornerShape(conerRadius)),
                    alignment = Alignment.TopStart,
                    contentScale = ContentScale.FillHeight
                )
            }
        }
    }
}