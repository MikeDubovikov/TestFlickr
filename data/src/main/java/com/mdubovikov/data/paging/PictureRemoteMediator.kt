package com.mdubovikov.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mdubovikov.data.database.FlickrDatabase
import com.mdubovikov.data.database.entity.PictureDb
import com.mdubovikov.data.gallery_screen.PictureMapper
import com.mdubovikov.data.network.api.FlickrApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PictureRemoteMediator @Inject constructor(
    private val flickrApi: FlickrApi,
    private val flickrDatabase: FlickrDatabase,
    private val pictureMapper: PictureMapper,
    private val query: String,
    private val quality: String
) : RemoteMediator<Int, PictureDb>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PictureDb>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val pictures = flickrApi.search(
                text = query,
                page = loadKey,
                count = state.config.pageSize
            ).photos.photo

            if (!pictures.isEmpty()) {
                flickrDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        flickrDatabase.flickrDao().clearPictures()
                        flickrDatabase.flickrDao().resetAutoincrement()
                    }

                    val picturesDb = pictures.map { dto ->
                        pictureMapper.toPictureDb(
                            pictureDto = dto,
                            quality = quality
                        )
                    }

                    flickrDatabase.flickrDao().upsertPictures(picturesDb)
                }

                MediatorResult.Success(
                    endOfPaginationReached = pictures.isEmpty()
                )
            } else {
                throw RuntimeException("Query is empty")
            }

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: RuntimeException) {
            MediatorResult.Error(e)
        }
    }
}