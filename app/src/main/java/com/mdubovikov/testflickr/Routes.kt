package com.mdubovikov.testflickr

import kotlinx.serialization.Serializable

sealed class Route {

    @Serializable
    data object GalleryScreen : Route()

    @Serializable
    data class PictureDetailScreen(val pictureUrl: String) : Route()
}