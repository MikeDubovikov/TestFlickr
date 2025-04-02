package com.mdubovikov.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pictures",
    indices = [Index(value = ["idPicture"], unique = true)]
)
data class PictureDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "idPicture")
    val idPicture: Long,
    val url: String
)