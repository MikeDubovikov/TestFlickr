package com.mdubovikov.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mdubovikov.data.database.entity.PictureDb

@Dao
interface FlickrDao {

    @Upsert
    suspend fun upsertPictures(pictures: List<PictureDb>)

    @Query("SELECT * FROM pictures")
    fun getPicturesPaging(): PagingSource<Int, PictureDb>

    @Query("SELECT * FROM pictures")
    suspend fun getPicturesList(): List<PictureDb>

    @Query("DELETE FROM pictures")
    suspend fun clearPictures()

    @Query("DELETE FROM sqlite_sequence WHERE name='pictures'")
    suspend fun resetAutoincrement()
}