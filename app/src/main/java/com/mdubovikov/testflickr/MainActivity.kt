package com.mdubovikov.testflickr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mdubovikov.gallery_screen.presentation.GalleryScreen
import com.mdubovikov.gallery_screen.presentation.GalleryViewModel
import com.mdubovikov.picture_detail_screen.presentation.PictureDetailScreen
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

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Route.GalleryScreen
                ) {
                    composable<Route.GalleryScreen> {
                        GalleryScreen(
                            viewModel = galleryViewModel,
                            onDetailClick = {
                                navController.navigate(Route.PictureDetailScreen(pictureUrl = it))
                            }
                        )
                    }

                    composable<Route.PictureDetailScreen> { backStackEntry ->
                        val args = backStackEntry.toRoute<Route.PictureDetailScreen>()
                        PictureDetailScreen(
                            pictureUrl = args.pictureUrl,
                            onBackClick = { navController.navigateUp() }
                        )
                    }
                }
            }
        }
    }
}