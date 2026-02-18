package com.pranshulgg.watchmaster.data

import com.pranshulgg.watchmaster.model.WatchStatus

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

data class TvSimple(
    val id: Long,
    val name: String,
    val poster_path: String,
    val season_number: Int,
)