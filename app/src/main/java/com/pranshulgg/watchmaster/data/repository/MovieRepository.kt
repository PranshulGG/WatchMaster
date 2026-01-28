package com.pranshulgg.watchmaster.data.repository

import android.util.Log
import com.pranshulgg.watchmaster.data.local.dao.MovieBundleDao
import com.pranshulgg.watchmaster.data.local.entity.MovieBundle
import com.pranshulgg.watchmaster.data.local.entity.toDomain
import com.pranshulgg.watchmaster.data.local.mapper.toDomain
import com.pranshulgg.watchmaster.data.local.mapper.toEntity
import com.pranshulgg.watchmaster.network.TmdbApi

class MovieRepository(
    private val api: TmdbApi,
    private val dao: MovieBundleDao
) {

    suspend fun getWholeMovieData(movieId: Long): MovieBundle {

        dao.getById(movieId)?.let {
            return it.toDomain()

        }
        val response = api.getWholeMovieData(movieId)
        val body = response.body() ?: error("Movie not found")

        val domain = body.toDomain()

        dao.insert(domain.toEntity())

        return domain
    }

    suspend fun deleteCachedMovie(id: Long) {
        dao.deleteById(id)
    }
}
