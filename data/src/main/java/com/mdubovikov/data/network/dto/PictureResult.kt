package com.mdubovikov.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PictureResult(
    @SerialName("photos") val photos: PicturesDto,
    @SerialName("stat") val stat: String
)