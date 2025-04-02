package com.mdubovikov.gallery_screen.presentation

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mdubovikov.gallery_screen.domain.entity.Picture
import com.mdubovikov.gallery_screen.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val _pictures = MutableStateFlow<PagingData<Picture>>(PagingData.empty())
    val pictures = _pictures.asStateFlow()

    private val _query = MutableStateFlow<String>("")
    val query = _query.asStateFlow()

    private val _uiState = MutableStateFlow<GalleryUiState>(GalleryUiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun checkStatePictures(
        query: String = "",
        quality: String = "n"
    ) {
        if (query != "") {
            getPicturesPaging(query, quality)
        } else {
            getPicturesList()
        }
    }

    private fun getPicturesPaging(query: String, quality: String) {

        val pagingFlow = galleryRepository.searchPictures(
            query = query,
            quality = quality
        ).cachedIn(viewModelScope)

        viewModelScope.launch {
            pagingFlow.collect { pagingData ->
                _pictures.value = pagingData
                _uiState.value = GalleryUiState.Success
            }
        }
    }

    private fun getPicturesList() {
        viewModelScope.launch {
            val pictures = galleryRepository.getPictures()
            if (!pictures.isEmpty()) {
                _pictures.value = PagingData.from(pictures)
                _uiState.value = GalleryUiState.Success
            } else {
                _uiState.value = GalleryUiState.Empty
            }
        }
    }

    fun updateQuery(query: String) {
        _query.value = query.replaceFirstChar { it.uppercase() }.trim()
    }
}

@Stable
sealed interface GalleryUiState {

    data object Initial : GalleryUiState

    data object Empty : GalleryUiState

    data object Success : GalleryUiState
}