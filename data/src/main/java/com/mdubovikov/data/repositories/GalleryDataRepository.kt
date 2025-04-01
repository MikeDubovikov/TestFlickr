package com.mdubovikov.data.repositories

import androidx.paging.PagingData
import com.mdubovikov.data.database.entity.PictureDb
import com.mdubovikov.data.network.dto.PictureDto
import kotlinx.coroutines.flow.Flow

interface GalleryDataRepository {

    fun searchPictures(query: String, quality: String): Flow<PagingData<PictureDto>>

    suspend fun getPictures(): List<PictureDb>

    suspend fun insertPictures(pictures: List<PictureDb>)

    suspend fun clearPictures()
}