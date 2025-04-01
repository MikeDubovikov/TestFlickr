package com.mdubovikov.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mdubovikov.data.database.entity.PictureDb

@Dao
interface FlickrDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertPictures(pictures: List<PictureDb>)

    @Query("DELETE FROM pictures")
    suspend fun clearPictures()

    @Query("SELECT * FROM pictures")
    suspend fun getPictures(): List<PictureDb>
}