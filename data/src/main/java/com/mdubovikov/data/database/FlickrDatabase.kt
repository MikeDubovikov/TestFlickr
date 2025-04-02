package com.mdubovikov.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mdubovikov.data.database.dao.FlickrDao
import com.mdubovikov.data.database.entity.PictureDb

@Database(entities = [PictureDb::class], version = 1)
abstract class FlickrDatabase : RoomDatabase() {

    abstract fun flickrDao(): FlickrDao

    companion object {

        private const val DB_NAME = "FlickrDatabase"
        private var INSTANCE: FlickrDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): FlickrDatabase {
            INSTANCE?.let { return it }

            synchronized(LOCK) {
                INSTANCE?.let { return it }

                val database = Room.databaseBuilder(
                    context = context,
                    klass = FlickrDatabase::class.java,
                    name = DB_NAME
                ).build()

                INSTANCE = database
                return database
            }
        }
    }
}