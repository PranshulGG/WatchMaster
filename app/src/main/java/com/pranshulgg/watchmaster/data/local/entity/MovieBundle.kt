package com.pranshulgg.watchmaster.data.local.entity

import com.pranshulgg.watchmaster.data.CreditsDto
import com.pranshulgg.watchmaster.data.Genre
import com.pranshulgg.watchmaster.data.ImagesDto
import com.pranshulgg.watchmaster.data.MovieListDto
import com.pranshulgg.watchmaster.data.ReleaseDatesDto
import com.pranshulgg.watchmaster.data.ReviewsDto
import com.pranshulgg.watchmaster.data.VideosDto
import com.pranshulgg.watchmaster.data.WatchProvidersDto
import com.pranshulgg.watchmaster.network.MovieBundleDto

data class MovieBundle(
    val id: Long,
    val title: String,
    val overview: String,
    val runtime: Int?,
    val genres: List<Genre>,
    val credits: CreditsDto,
    val videos: VideosDto,
    val images: ImagesDto,
    val watchProviders: WatchProvidersDto?,
    val similar: MovieListDto,
    val recommendations: MovieListDto,
    val reviews: ReviewsDto,
    val releaseDates: ReleaseDatesDto
)

// mapper extension
fun MovieBundleDto.toDomain(): MovieBundle = MovieBundle(
    id = id,
    title = title,
    overview = overview,
    runtime = runtime,
    genres = genres,
    credits = credits,
    videos = videos,
    images = images,
    watchProviders = watchProviders,
    similar = similar,
    recommendations = recommendations,
    reviews = reviews,
    releaseDates = release_dates
)
