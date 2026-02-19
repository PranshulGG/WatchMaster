package com.pranshulgg.watchmaster.data.local.entity

import com.pranshulgg.watchmaster.data.CreditsDto
import com.pranshulgg.watchmaster.data.EpisodeListDto
import com.pranshulgg.watchmaster.data.Genre
import com.pranshulgg.watchmaster.data.ImagesDto
import com.pranshulgg.watchmaster.data.MovieListDto
import com.pranshulgg.watchmaster.data.ReviewsDto
import com.pranshulgg.watchmaster.data.TvCreditsDto
import com.pranshulgg.watchmaster.data.TvListDto
import com.pranshulgg.watchmaster.data.TvReviewsDto
import com.pranshulgg.watchmaster.data.TvWatchProvidersDto
import com.pranshulgg.watchmaster.data.WatchProvidersDto
import com.pranshulgg.watchmaster.data.local.dao.TvBundleDao
import com.pranshulgg.watchmaster.network.MovieBundleDto
import com.pranshulgg.watchmaster.network.TvBundleDto

data class TvBundle(
    val id: Long,
    val name: String,
    val overview: String,
    val runtime: Int?,
    val season_number: Int,
    val poster_path: String?,
    val backdrop_path: String?,
    val episodes: EpisodeListDto,
    val genres: List<Genre>,
    val credits: TvCreditsDto,
    val watchProviders: TvWatchProvidersDto?,
//    val similar: TvListDto,
//    val recommendations: TvListDto,
    val reviews: TvReviewsDto,
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
    episodes = episodes,
    genres = genres,
    credits = credits,
    watchProviders = watchProviders,
//    similar = similar,
//    recommendations = recommendations,
    reviews = reviews,
)
