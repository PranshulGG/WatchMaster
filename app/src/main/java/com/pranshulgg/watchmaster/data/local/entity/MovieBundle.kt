package com.pranshulgg.watchmaster.data.local.entity

import com.pranshulgg.watchmaster.data.CreditsDto
import com.pranshulgg.watchmaster.data.ImagesDto
import com.pranshulgg.watchmaster.data.MovieListDto
import com.pranshulgg.watchmaster.data.ReviewsDto
import com.pranshulgg.watchmaster.data.WatchProvidersDto
import com.pranshulgg.watchmaster.core.network.MovieBundleDto
import com.pranshulgg.watchmaster.data.MovieGenre

data class MovieBundle(
    val id: Long,
    val title: String,
    val overview: String,
    val runtime: Int?,
    val poster_path: String?,
    val backdrop_path: String?,
    val genres: List<MovieGenre>,
    val credits: CreditsDto,
    val images: ImagesDto,
    val watchProviders: WatchProvidersDto?,
    val similar: MovieListDto,
    val recommendations: MovieListDto,
    val reviews: ReviewsDto,
)

// mapper extension
fun MovieBundleDto.toDomain(): MovieBundle = MovieBundle(
    id = id,
    title = title,
    overview = overview,
    runtime = runtime,
    poster_path = poster_path,
    backdrop_path = backdrop_path,
    genres = genres,
    credits = credits,
    images = images,
    watchProviders = watchProviders,
    similar = similar,
    recommendations = recommendations,
    reviews = reviews,
)
