package com.pranshulgg.watchmaster.screens.search

import com.pranshulgg.watchmaster.model.SearchType
import com.pranshulgg.watchmaster.network.TmdbApi

class SearchRepository(
    private val api: TmdbApi
) {
    suspend fun search(
        query: String,
        type: SearchType = SearchType.MULTI
    ): List<SearchItem> {

        val resp = api.search(type.endpoint, query)


        if (!resp.isSuccessful) return emptyList()

        return resp.body()?.results
            ?.mapNotNull { r ->

                val mediaType = r.media_type ?: when (type) {
                    SearchType.MOVIE -> "movie"
                    SearchType.TV -> "tv"
                    SearchType.PERSON -> "person"
                    else -> "unknown"
                }

                val title = r.title ?: r.name
                ?: r.known_for?.firstOrNull()?.title
                ?: r.known_for?.firstOrNull()?.name
                ?: return@mapNotNull null

                val knownFor = r.known_for?.firstOrNull()

                val poster = when (mediaType) {
                    "movie", "tv" -> r.poster_path
                    "person" -> r.profile_path ?: knownFor?.poster_path
                    else -> null
                }

                val genreIds = when (mediaType) {
                    "movie", "tv" -> r.genreIds
                    "person" -> knownFor?.genreIds
                    else -> null
                }

                val releaseDate = when (mediaType) {
                    "movie" -> r.releaseDate
                    "tv" -> r.firstAirDate
                    "person" -> knownFor?.releaseDate ?: knownFor?.firstAirDate
                    else -> null
                }

                val originalLanguage = when (mediaType) {
                    "movie", "tv" -> r.originalLanguage
                    "person" -> knownFor?.originalLanguage
                    else -> null
                }

                SearchItem(
                    id = r.id,
                    mediaType = mediaType,
                    title = title,
                    overview = r.overview,
                    posterPath = poster,
                    genreIds = genreIds,
                    releaseDate = releaseDate,
                    originalLanguage = originalLanguage,
                    avg_rating = r.vote_average
                )
            } ?: emptyList()
    }
}
