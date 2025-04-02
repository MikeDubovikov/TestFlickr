package com.mdubovikov.testflickr.merging

import androidx.paging.PagingData
import androidx.paging.map
import com.mdubovikov.data.gallery_screen.PictureMapper
import com.mdubovikov.data.repositories.GalleryDataRepository
import com.mdubovikov.gallery_screen.domain.entity.Picture
import com.mdubovikov.gallery_screen.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterGalleryRepository @Inject constructor(
    private val galleryDataRepository: GalleryDataRepository,
    private val pictureMapper: PictureMapper
) : GalleryRepository {

    override fun searchPictures(query: String, quality: String): Flow<PagingData<Picture>> =
        galleryDataRepository.searchPictures(
            query = query,
            quality = quality
        ).map { pagingData -> pagingData.map(pictureMapper::toPictureDomain) }

    override suspend fun getPictures(): List<Picture> = galleryDataRepository.getPictures()
}