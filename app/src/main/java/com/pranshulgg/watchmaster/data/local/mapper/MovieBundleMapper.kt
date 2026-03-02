package com.pranshulgg.watchmaster.data.local.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pranshulgg.watchmaster.data.*
import com.pranshulgg.watchmaster.data.local.entity.MovieBundleEntity
import com.pranshulgg.watchmaster.data.local.entity.MovieBundle

private val gson = Gson()

fun MovieBundle.toEntity(): MovieBundleEntity =
    MovieBundleEntity(
        id = id,
        title = title,
        overview = overview,
        runtime = runtime,
        poster_path = poster_path,
        backdrop_path = backdrop_path,
        genresJson = gson.toJson(genres),
        creditsJson = gson.toJson(credits),
        imagesJson = gson.toJson(images),
        watchProvidersJson = watchProviders?.let { gson.toJson(it) },
        similarJson = gson.toJson(similar),
        recommendationsJson = gson.toJson(recommendations),
        reviewsJson = gson.toJson(reviews),
        cachedAt = System.currentTimeMillis()
    )

fun MovieBundleEntity.toDomain(): MovieBundle =
    MovieBundle(
        id = id,
        title = title,
        overview = overview,
        runtime = runtime,
        poster_path = poster_path,
        backdrop_path = backdrop_path,
        genres = gson.fromJson(
            genresJson,
            object : TypeToken<List<MovieGenre>>() {}.type
        ),
        credits = gson.fromJson(creditsJson, CreditsDto::class.java),
        images = gson.fromJson(imagesJson, ImagesDto::class.java),
        watchProviders = watchProvidersJson?.let {
            gson.fromJson(it, WatchProvidersDto::class.java)
        },
        similar = gson.fromJson(similarJson, MovieListDto::class.java),
        recommendations = gson.fromJson(recommendationsJson, MovieListDto::class.java),
        reviews = gson.fromJson(reviewsJson, ReviewsDto::class.java),
    )
