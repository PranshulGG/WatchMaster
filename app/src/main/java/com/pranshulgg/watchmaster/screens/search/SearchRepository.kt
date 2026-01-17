package com.pranshulgg.watchmaster.screens.search

import com.pranshulgg.watchmaster.network.TmdbApi

class SearchRepository(
    private val api: TmdbApi
) {
    suspend fun search(query: String): List<SearchItem> {
        val resp = api.multiSearch(query)

        if (!resp.isSuccessful) return emptyList()

        return resp.body()?.results
            ?.filter { it.media_type != "person" }
            ?.mapNotNull { r ->

                val title = r.title ?: r.name
                ?: r.known_for?.firstOrNull()?.title
                ?: r.known_for?.firstOrNull()?.name
                ?: return@mapNotNull null

                val knownFor = r.known_for?.firstOrNull()

                val poster = when (r.media_type) {
                    "movie", "tv" -> r.poster_path
                    "person" -> r.profile_path ?: knownFor?.poster_path
                    else -> null
                }

                val genreIds = when (r.media_type) {
                    "movie", "tv" -> r.genreIds
                    "person" -> knownFor?.genreIds
                    else -> null
                }

                val releaseDate = when (r.media_type) {
                    "movie" -> r.releaseDate
                    "tv" -> r.firstAirDate
                    "person" -> knownFor?.releaseDate ?: knownFor?.firstAirDate
                    else -> null
                }
                if (poster == null && genreIds == null && releaseDate == null) {
                    return@mapNotNull null
                }
                SearchItem(
                    id = r.id,
                    mediaType = r.media_type ?: "unknown",
                    title = title,
                    overview = r.overview,
                    posterPath = poster,
                    genreIds = genreIds,
                    releaseDate = releaseDate
                )
            } ?: emptyList()
    }
}
