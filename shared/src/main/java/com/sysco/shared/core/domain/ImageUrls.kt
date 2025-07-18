package com.sysco.shared.core.domain

object ImageUrls {
    fun getDynamicSmallImageUrl(id: Int): String = "https://picsum.photos/id/$id/200"

    fun getDynamicLargeImageUrl(id: Int): String = "https://picsum.photos/id/$id/1920"
}