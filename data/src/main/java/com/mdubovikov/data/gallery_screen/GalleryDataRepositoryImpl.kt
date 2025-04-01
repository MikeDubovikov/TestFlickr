package com.mdubovikov.data.gallery_screen

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mdubovikov.data.database.dao.FlickrDao
import com.mdubovikov.data.database.entity.PictureDb
import com.mdubovikov.data.network.api.FlickrApi
import com.mdubovikov.data.network.dto.PictureDto
import com.mdubovikov.data.paging.PicturePageSource
import com.mdubovikov.data.repositories.GalleryDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GalleryDataRepositoryImpl @Inject constructor(
    private val apiService: FlickrApi,
    private val flickrDao: FlickrDao
) : GalleryDataRepository {

    override fun searchPictures(query: String, quality: String): Flow<PagingData<PictureDto>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = { PicturePageSource(apiService, query) }
        ).flow

    override suspend fun getPictures(): List<PictureDb> {
        return flickrDao.getPictures()
    }

    override suspend fun insertPictures(pictures: List<PictureDb>) {
        return flickrDao.insertPictures(pictures = pictures)
    }

    override suspend fun clearPictures() {
        return flickrDao.clearPictures()
    }
}