package com.mdubovikov.gallery_screen.presentation

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.mdubovikov.gallery_screen.domain.entity.Picture

@Composable
internal fun CharactersList(
    pictures: LazyPagingItems<Picture>
) {
    val scrollableListState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = scrollableListState
    ) {
        items(pictures.itemCount) { item ->
            pictures[item]?.let { picture ->
                PictureCard(picture = picture)
            }
        }
    }
}