package com.sysco.common.presentation.image

import android.content.Context
import coil.request.CachePolicy
import coil.request.ImageRequest


fun noCacheImageRequest(
    context: Context,
    imageUrl: String,
): ImageRequest = ImageRequest.Builder(context)
    .data(imageUrl)
    .memoryCachePolicy(CachePolicy.DISABLED)
    .diskCachePolicy(CachePolicy.DISABLED)
    .crossfade(true)
    .build()
