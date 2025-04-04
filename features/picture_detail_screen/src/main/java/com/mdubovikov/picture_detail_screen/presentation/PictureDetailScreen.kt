package com.mdubovikov.picture_detail_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.mdubovikov.ui.ErrorState
import com.mdubovikov.ui.R
import com.mdubovikov.ui.TopBar

@Composable
fun PictureDetailScreen(
    pictureUrl: String?,
    onBackClick: () -> Unit
) {

    val painter = rememberAsyncImagePainter(pictureUrl?.replaceQuality("b"))
    val imageState = painter.state

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val state = rememberTransformableState { zoomChange, panChange, rotationChange ->
        scale = (scale * zoomChange).coerceIn(1f, 5f)

        offset = Offset(
            x = (offset.x + scale * panChange.x),
            y = (offset.y + scale * panChange.y)
        )
    }

    Scaffold(
        topBar = {
            TopBar(
                screenName = stringResource(R.string.picture),
                onBackClick = { onBackClick() }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .pointerInput(Unit) {
                    detectTapGestures(onDoubleTap = {
                        scale = if (scale > 1f) 1f else 2f
                        offset = Offset.Zero
                    })
                }
        ) {
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.image),
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
                    .transformable(state),
                contentScale = ContentScale.Fit
            )

            if (imageState is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (imageState is AsyncImagePainter.State.Error) {
                ErrorState()
            }
        }
    }
}

private fun String.replaceQuality(quality: String): String =
    this.replace(Regex("_\\w\\.jpg$"), "_$quality.jpg")