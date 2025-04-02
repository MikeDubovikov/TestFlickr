package com.mdubovikov.data.gallery_screen

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mdubovikov.data.database.FlickrDatabase
import com.mdubovikov.data.database.entity.PictureDb
import com.mdubovikov.data.network.api.FlickrApi
import com.mdubovikov.data.paging.PictureRemoteMediator
import com.mdubovikov.data.repositories.GalleryDataRepository
import com.mdubovikov.gallery_screen.domain.entity.Picture
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GalleryDataRepositoryImpl @Inject constructor(
    private val flickrApi: FlickrApi,
    private val flickrDatabase: FlickrDatabase,
    private val pictureMapper: PictureMapper
) : GalleryDataRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun searchPictures(
        query: String,
        quality: String
    ): Flow<PagingData<PictureDb>> =
        Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = PictureRemoteMediator(
                flickrApi,
                flickrDatabase,
                pictureMapper,
                query,
                quality
            ),
            pagingSourceFactory = { flickrDatabase.flickrDao().getPicturesPaging() }
        ).flow

    override suspend fun getPictures(): List<Picture> =
        flickrDatabase.flickrDao().getPicturesList().map(pictureMapper::toPictureDomain)
}