package com.pranshulgg.watchmaster.data.local.entity

import com.pranshulgg.watchmaster.data.TvCreditsDto
import com.pranshulgg.watchmaster.data.TvReviewsDto
import com.pranshulgg.watchmaster.data.TvWatchProvidersDto
import com.pranshulgg.watchmaster.core.network.TvBundleDto
import com.pranshulgg.watchmaster.data.TvGenre

data class TvBundle(
    val id: Long,
    val name: String,
    val overview: String,
    val runtime: Int?,
    val season_number: Int,
    val poster_path: String?,
    val backdrop_path: String?,
    val genres: List<TvGenre>,
    val credits: TvCreditsDto,
    val watchProviders: TvWatchProvidersDto?,
//    val similar: TvListDto,
//    val recommendations: TvListDto,
    val reviews: TvReviewsDto,
    val cachedAt: Long
)

// mapper extension
fun TvBundleDto.toDomain(): TvBundle = TvBundle(
    id = id,
    name = name,
    overview = overview,
    runtime = runtime,
    season_number = season_number,
    poster_path = poster_path,
    backdrop_path = backdrop_path,
//    episodes = episodes,
    genres = genres,
    credits = credits,
    watchProviders = watchProviders,
//    similar = similar,
//    recommendations = recommendations,
    reviews = reviews,
    cachedAt = System.currentTimeMillis()
)
