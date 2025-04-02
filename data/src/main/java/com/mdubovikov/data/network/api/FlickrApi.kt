package com.mdubovikov.data.network.api

import com.mdubovikov.data.network.dto.PictureResult
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET(SEARCH_URL)
    suspend fun search(
        @Query("tags") text: String,
        @Query("page") page: Int,
        @Query("per_page") count: Int
    ): PictureResult

    companion object {
        private const val SEARCH_URL =
            "services/rest/?method=flickr.photos.search&api_key=da9d38d3dee82ec8dda8bb0763bf5d9c" +
                    "&format=json&nojsoncallback=1&sort=relevance"
    }
}