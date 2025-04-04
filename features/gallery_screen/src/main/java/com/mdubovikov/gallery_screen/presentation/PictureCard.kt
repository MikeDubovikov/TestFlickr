package com.mdubovikov.gallery_screen.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mdubovikov.gallery_screen.domain.entity.Picture
import com.mdubovikov.ui.R

@Composable
fun PictureCard(
    modifier: Modifier = Modifier,
    picture: Picture,
    onDetailClick: (pictureUrl: String) -> Unit
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onDetailClick(picture.url) }),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(picture.url)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            filterQuality = FilterQuality.None,
            contentDescription = stringResource(R.string.image)
        )
    }
}