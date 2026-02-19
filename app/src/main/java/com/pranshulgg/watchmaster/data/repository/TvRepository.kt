package com.pranshulgg.watchmaster.data.repository

import com.pranshulgg.watchmaster.data.local.dao.MovieBundleDao
import com.pranshulgg.watchmaster.data.local.dao.TvBundleDao
import com.pranshulgg.watchmaster.data.local.entity.MovieBundle
import com.pranshulgg.watchmaster.data.local.entity.TvBundle
import com.pranshulgg.watchmaster.data.local.entity.toDomain
import com.pranshulgg.watchmaster.data.local.mapper.toDomain
import com.pranshulgg.watchmaster.data.local.mapper.toEntity
import com.pranshulgg.watchmaster.network.TmdbApi

class TvRepository(
    private val api: TmdbApi,
    private val dao: TvBundleDao
) {

    suspend fun getWholeTvData(tvId: Long, seasonNumber: Int): TvBundle {

        dao.getById(tvId)?.let {
            return it.toDomain()
        }

        val response = api.getWholeTvData(tvId, seasonNumber)
        val body = response.body() ?: error("TV season not found")

        val domain = body.toDomain()
        dao.insert(domain.toEntity())

        return domain
    }

    suspend fun deleteCachedTv(id: Long) {
        dao.deleteById(id)
    }
}
