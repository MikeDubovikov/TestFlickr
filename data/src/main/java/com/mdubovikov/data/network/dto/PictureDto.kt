package com.mdubovikov.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PictureDto(
    @SerialName("id") val id: String,
    @SerialName("owner") val owner: String,
    @SerialName("secret") val secret: String,
    @SerialName("server") val server: String,
    @SerialName("farm") val farm: Int,
    @SerialName("title") val title: String,
    @SerialName("isfriend") val isFriend: Int,
    @SerialName("ispublic") val isPublic: Int,
    @SerialName("isfamily") val isFamily: Int
)