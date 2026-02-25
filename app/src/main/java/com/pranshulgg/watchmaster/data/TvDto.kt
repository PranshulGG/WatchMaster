package com.pranshulgg.watchmaster.data

import com.pranshulgg.watchmaster.core.model.WatchStatus

data class TvCreditsDto(
    val cast: List<TvCastMember>,
    val crew: List<TvCrewMember>
)

data class TvCastMember(
    val id: Long,
    val name: String,
    val character: String?,
    val profile_path: String?
)

data class TvCrewMember(
    val id: Long,
    val name: String,
    val job: String?,
    val department: String?,
    val profile_path: String?
)

data class EpisodeListDto(
    val episodes: List<EpisodeItem>,
)


data class EpisodeItem(
    val air_date: String,
    val episode_number: Int,
    val id: Long,
    val name: String,
    val overview: String,
    val season_number: Int,
    val runtime: Int,
    val isWatched: Boolean = false
)

data class TvListDto(
    val page: Int,
    val results: List<TvSimple>,
    val total_pages: Int,
    val total_results: Int
)

data class TvSimple(
    val id: Long,
    val name: String,
    val poster_path: String,
    val season_number: Int,
)

data class TvWatchProvidersDto(
    val results: Map<String, CountryWatchProviders>
)

data class TvCountryWatchProviders(
    val link: String?,
    val flatrate: List<Provider>?,
    val rent: List<Provider>?,
    val buy: List<Provider>?
)

data class TvProvider(
    val provider_id: Int,
    val provider_name: String,
    val logo_path: String?
)

data class TvReviewsDto(
    val page: Int,
    val results: List<ReviewItem>,
    val total_pages: Int,
    val total_results: Int
)

data class TvReviewItem(
    val id: String,
    val author: String,
    val content: String,
    val url: String
)