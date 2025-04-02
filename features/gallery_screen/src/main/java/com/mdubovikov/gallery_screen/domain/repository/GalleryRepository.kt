package com.mdubovikov.gallery_screen.domain.repository

import androidx.paging.PagingData
import com.mdubovikov.gallery_screen.domain.entity.Picture
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {

    fun searchPictures(query: String, quality: String): Flow<PagingData<Picture>>

    suspend fun getPictures(): List<Picture>
}