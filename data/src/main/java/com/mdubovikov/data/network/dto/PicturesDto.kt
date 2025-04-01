package com.mdubovikov.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PicturesDto(
    @SerialName("page") val page: Int,
    @SerialName("pages") val pages: Int,
    @SerialName("perpage") val perPage: Int,
    @SerialName("total") val total: Int,
    @SerialName("photo") val photo: List<PictureDto>
)