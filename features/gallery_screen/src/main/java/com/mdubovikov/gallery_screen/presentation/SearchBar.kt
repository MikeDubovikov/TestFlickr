package com.mdubovikov.gallery_screen.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mdubovikov.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPicturesBar(
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel
) {
    var query = viewModel.query.collectAsState()

    val focusManager = LocalFocusManager.current

    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        inputField = {
            SearchBarDefaults.InputField(
                query = query.value,
                onQueryChange = { viewModel.updateQuery(query = it) },
                onSearch = {
                    viewModel.checkStatePictures(query = it)
                    focusManager.clearFocus()
                },
                expanded = false,
                onExpandedChange = { },
                placeholder = { Text(text = stringResource(R.string.search)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = stringResource(R.string.search_icon)
                    )
                },
                trailingIcon = {
                    if (query.value.isNotEmpty()) {
                        Icon(
                            modifier = Modifier.clickable {
                                viewModel.updateQuery("")
                                focusManager.clearFocus()
                            },
                            imageVector = Icons.Default.Clear,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = stringResource(R.string.clear_search_icon)
                        )
                    }
                }
            )
        },
        expanded = false,
        onExpandedChange = { }
    ) {}
}