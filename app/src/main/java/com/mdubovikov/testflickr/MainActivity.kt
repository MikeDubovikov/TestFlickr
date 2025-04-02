package com.mdubovikov.testflickr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.mdubovikov.gallery_screen.presentation.GalleryScreen
import com.mdubovikov.gallery_screen.presentation.GalleryViewModel
import com.mdubovikov.ui.theme.TestFlickrTheme
import com.mdubovikov.utils.ViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    private val applicationComponent by lazy {
        (application as TestFlickrApp).applicationComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val galleryViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GalleryViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        applicationComponent.activityInject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestFlickrTheme {
                GalleryScreen(viewModel = galleryViewModel)
            }
        }
    }
}