package com.pranshulgg.watchmaster.data.local.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
import com.pranshulgg.watchmaster.data.local.entity.MovieBundle
import com.pranshulgg.watchmaster.data.local.entity.MovieBundleEntity
import com.pranshulgg.watchmaster.data.local.entity.TvBundle
import com.pranshulgg.watchmaster.data.local.entity.TvBundleEntity


private val gson = Gson()
fun TvBundle.toEntity(): TvBundleEntity =
    TvBundleEntity(
        id = id,
        name = name,
        overview = overview,
        runtime = runtime,
        poster_path = poster_path,
        season_number = season_number,
        backdrop_path = backdrop_path,
        episodeJson = gson.toJson(episodes),
        genresJson = gson.toJson(genres),
        creditsJson = gson.toJson(credits),
        watchProvidersJson = watchProviders?.let { gson.toJson(it) },
//        similarJson = gson.toJson(similar),
//        recommendationsJson = gson.toJson(recommendations),
        reviewsJson = gson.toJson(reviews),
        cachedAt = System.currentTimeMillis()
    )


fun TvBundleEntity.toDomain(): TvBundle =
    TvBundle(
        id = id,
        name = name,
        overview = overview,
        runtime = runtime,
        poster_path = poster_path,
        backdrop_path = backdrop_path,
        season_number = season_number,
        episodes = gson.fromJson(episodeJson, EpisodeListDto::class.java),
        genres = gson.fromJson(
            genresJson,
            object : TypeToken<List<Genre>>() {}.type
        ),
        credits = gson.fromJson(creditsJson, TvCreditsDto::class.java),
        watchProviders = watchProvidersJson?.let {
            gson.fromJson(it, TvWatchProvidersDto::class.java)
        },
//        similar = gson.fromJson(similarJson, TvListDto::class.java),
//        recommendations = gson.fromJson(recommendationsJson, TvListDto::class.java),
        reviews = gson.fromJson(reviewsJson, TvReviewsDto::class.java),

        )
