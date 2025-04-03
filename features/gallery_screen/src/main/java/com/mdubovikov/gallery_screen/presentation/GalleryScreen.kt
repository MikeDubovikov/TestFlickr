package com.mdubovikov.gallery_screen.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.mdubovikov.ui.ErrorState
import com.mdubovikov.ui.R
import com.mdubovikov.ui.TopBar

@Composable
fun GalleryScreen(
    viewModel: GalleryViewModel,
    onDetailClick: (pictureUrl: String) -> Unit
) {

    val pictures = viewModel.pictures.collectAsLazyPagingItems()

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkStatePictures()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopBar(
                screenName = stringResource(R.string.search),
                onBackClick = {}
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            SearchPicturesBar(
                modifier = Modifier.padding(top = 60.dp, bottom = 16.dp),
                viewModel = viewModel
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (state) {
                    GalleryUiState.Initial -> {
                        Log.d("GalleryScreen", "Initial state reached")
                    }

                    GalleryUiState.Empty -> {
                        Text(
                            text = stringResource(R.string.make_search),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.alpha(0.5f)
                        )
                    }

                    GalleryUiState.Success -> {
                        CharactersList(
                            pictures = pictures,
                            onDetailClick = onDetailClick
                        )
                    }
                }
            }
        }

        if (pictures.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (pictures.loadState.refresh is LoadState.Error) {
            ErrorState()
        }
    }
}