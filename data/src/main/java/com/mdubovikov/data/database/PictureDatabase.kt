package com.mdubovikov.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mdubovikov.data.database.dao.FlickrDao
import com.mdubovikov.data.database.entity.PictureDb

@Database(entities = [PictureDb::class], version = 1, exportSchema = false)
abstract class PictureDatabase : RoomDatabase() {

    abstract fun getPictureDao(): FlickrDao

    companion object {

        private const val DB_NAME = "PictureDatabase"
        private var INSTANCE: PictureDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): PictureDatabase {
            INSTANCE?.let { return it }

            synchronized(LOCK) {
                INSTANCE?.let { return it }

                val database = Room.databaseBuilder(
                    context = context,
                    klass = PictureDatabase::class.java,
                    name = DB_NAME
                ).build()

                INSTANCE = database
                return database
            }
        }
    }
}