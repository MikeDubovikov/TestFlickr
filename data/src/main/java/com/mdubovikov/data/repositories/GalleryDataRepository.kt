package com.mdubovikov.data.repositories

import androidx.paging.PagingData
import com.mdubovikov.data.database.entity.PictureDb
import com.mdubovikov.gallery_screen.domain.entity.Picture
import kotlinx.coroutines.flow.Flow

interface GalleryDataRepository {

    fun searchPictures(query: String, quality: String): Flow<PagingData<PictureDb>>

    suspend fun getPictures(): List<Picture>
}