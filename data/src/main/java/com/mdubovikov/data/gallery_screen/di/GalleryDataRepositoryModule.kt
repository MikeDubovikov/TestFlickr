package com.mdubovikov.data.gallery_screen.di

import android.app.Application
import com.mdubovikov.data.database.FlickrDatabase
import com.mdubovikov.data.database.dao.FlickrDao
import com.mdubovikov.data.gallery_screen.GalleryDataRepositoryImpl
import com.mdubovikov.data.network.api.FlickrRetrofit
import com.mdubovikov.data.repositories.GalleryDataRepository
import com.mdubovikov.utils.ApplicationScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface GalleryDataRepositoryModule {

    @ApplicationScope
    @Binds
    fun bindsGalleryDataRepository(impl: GalleryDataRepositoryImpl): GalleryDataRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideFlickrApi() = FlickrRetrofit.apiService

        @ApplicationScope
        @Provides
        fun provideFlickrDatabase(application: Application): FlickrDatabase {
            return FlickrDatabase.getInstance(context = application)
        }

        @ApplicationScope
        @Provides
        fun provideFlickrDao(database: FlickrDatabase): FlickrDao {
            return database.flickrDao()
        }
    }
}