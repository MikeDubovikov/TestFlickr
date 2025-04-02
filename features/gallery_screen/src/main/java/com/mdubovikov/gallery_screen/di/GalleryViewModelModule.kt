package com.mdubovikov.gallery_screen.di

import androidx.lifecycle.ViewModel
import com.mdubovikov.gallery_screen.presentation.GalleryViewModel
import com.mdubovikov.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface GalleryViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GalleryViewModel::class)
    fun bindsGalleryViewModel(
        viewModel: GalleryViewModel
    ): ViewModel
}