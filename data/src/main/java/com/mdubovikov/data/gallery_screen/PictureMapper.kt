package com.mdubovikov.data.gallery_screen

import com.mdubovikov.data.database.entity.PictureDb
import com.mdubovikov.data.network.dto.PictureDto
import com.mdubovikov.gallery_screen.domain.entity.Picture
import javax.inject.Inject

class PictureMapper @Inject constructor() {

    fun toPictureDomain(pictureDb: PictureDb): Picture {
        return Picture(
            id = pictureDb.idPicture,
            url = pictureDb.url
        )
    }

    fun toPictureDb(pictureDto: PictureDto, quality: String): PictureDb {
        return PictureDb(
            idPicture = pictureDto.id.toLong(),
            url = "https://live.staticflickr.com/" +
                    "${pictureDto.server}/" +
                    "${pictureDto.id}_${pictureDto.secret}_${quality}.jpg"
        )
    }
}