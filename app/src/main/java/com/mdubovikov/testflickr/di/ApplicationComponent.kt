package com.mdubovikov.testflickr.di

import android.app.Application
import com.mdubovikov.data.gallery_screen.di.GalleryDataRepositoryModule
import com.mdubovikov.gallery_screen.di.GalleryViewModelModule
import com.mdubovikov.testflickr.MainActivity
import com.mdubovikov.utils.ApplicationScope
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        GalleryRepositoryModule::class,
        GalleryDataRepositoryModule::class,
        GalleryViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun activityInject(application: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}