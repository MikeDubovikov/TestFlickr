package com.mdubovikov.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pictures")
data class PictureDb(
    @PrimaryKey val id: String,
    val title: String,
    val url: String
)