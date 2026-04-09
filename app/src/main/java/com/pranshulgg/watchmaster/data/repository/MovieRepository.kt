package com.pranshulgg.watchmaster.data.repository

import android.util.Log
import com.pranshulgg.watchmaster.core.network.MovieBundleDto
import com.pranshulgg.watchmaster.data.local.dao.MovieBundleDao
import com.pranshulgg.watchmaster.data.local.entity.MovieBundle
import com.pranshulgg.watchmaster.data.local.entity.toDomain
import com.pranshulgg.watchmaster.data.local.mapper.toDomain
import com.pranshulgg.watchmaster.data.local.mapper.toEntity
import com.pranshulgg.watchmaster.core.network.TmdbApi
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import kotlin.math.log

class MovieRepository(
    private val api: TmdbApi,
    private val dao: MovieBundleDao
) {

    suspend fun getWholeMovieData(movieId: Long, forceFetch: Boolean = false): MovieBundle {

        if (!forceFetch) {
            dao.getById(movieId)?.let {
                return it.toDomain()
            }
        }

        val response = api.getWholeMovieData(movieId)
        val body = response.body() ?: throw Exception("Movie not found")

        val domain = body.toDomain()

        dao.insert(domain.toEntity())

        return domain
    }

    suspend fun deleteCachedMovie(id: Long) {
        dao.deleteById(id)
    }
}
