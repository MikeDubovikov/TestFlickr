package com.mdubovikov.testflickr.di

import com.mdubovikov.gallery_screen.domain.repository.GalleryRepository
import com.mdubovikov.testflickr.merging.AdapterGalleryRepository
import com.mdubovikov.utils.ApplicationScope
import dagger.Binds
import dagger.Module

@Module
interface GalleryRepositoryModule {

    @ApplicationScope
    @Binds
    fun bindsGalleryRepository(impl: AdapterGalleryRepository): GalleryRepository
}