package com.mdubovikov.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mdubovikov.data.network.api.FlickrApi
import com.mdubovikov.data.network.dto.PictureDto
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PicturePageSource @Inject constructor(
    private val apiService: FlickrApi,
    private val query: String
) : PagingSource<Int, PictureDto>() {

    override fun getRefreshKey(state: PagingState<Int, PictureDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PictureDto> {

        val page = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val response = apiService.search(
                text = query,
                page = page,
                count = pageSize
            )
            val pictures = response.photos.photo
            val nextKey = if (pictures.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(
                data = pictures,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}