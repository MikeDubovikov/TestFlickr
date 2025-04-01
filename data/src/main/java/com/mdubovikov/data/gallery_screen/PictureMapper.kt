package com.mdubovikov.data.gallery_screen

import com.mdubovikov.data.database.entity.PictureDb
import com.mdubovikov.data.network.dto.PictureDto
import com.mdubovikov.gallery_screen.domain.entity.Picture
import javax.inject.Inject

class PictureMapper @Inject constructor() {

    fun toPictureDomain(pictureDto: PictureDto, quality: String): Picture {
        return Picture(
            id = pictureDto.id,
            title = pictureDto.title,
            url = "https://live.staticflickr.com/" +
                    "${pictureDto.server}/" +
                    "${pictureDto.id}_${pictureDto.secret}_${quality}.jpg"
        )
    }

    fun toPictureDomain(pictureDb: PictureDb): Picture {
        return Picture(
            id = pictureDb.id,
            title = pictureDb.title,
            url = pictureDb.url
        )
    }

    fun toPictureDb(picture: Picture): PictureDb {
        return PictureDb(
            id = picture.id,
            title = picture.title,
            url = picture.url
        )
    }
}