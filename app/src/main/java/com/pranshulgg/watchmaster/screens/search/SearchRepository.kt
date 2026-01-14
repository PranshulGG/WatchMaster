package com.pranshulgg.watchmaster.screens.search

import com.pranshulgg.watchmaster.network.TmdbApi

class SearchRepository(
    private val api: TmdbApi
) {
    suspend fun search(query: String): List<SearchItem> {
        val resp = api.multiSearch(query)

        if (!resp.isSuccessful) return emptyList()

        return resp.body()?.results?.mapNotNull { r ->
            val title = r.title ?: r.name ?: return@mapNotNull null
            SearchItem(
                id = r.id,
                mediaType = r.media_type ?: "unknown",
                title = title,
                overview = r.overview,
                posterPath = r.poster_path
            )
        } ?: emptyList()
    }
}
