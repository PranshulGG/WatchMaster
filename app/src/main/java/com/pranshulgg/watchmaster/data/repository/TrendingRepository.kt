package com.pranshulgg.watchmaster.data.repository

import android.util.Log
import com.pranshulgg.watchmaster.core.model.Trending
import com.pranshulgg.watchmaster.core.network.TmdbApi
import com.pranshulgg.watchmaster.data.local.dao.TrendingDao
import com.pranshulgg.watchmaster.data.local.mapper.toDomain
import com.pranshulgg.watchmaster.data.local.mapper.toEntity
import jakarta.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class TrendingRepository @Inject constructor(
    private val api: TmdbApi,
    private val dao: TrendingDao
) {

    suspend fun fetchTrending(
        mediaType: String,
        page: Int,
        trendingType: String
    ): List<Trending> {


        dao.getTrending(trendingType).let {
            if (it.isNotEmpty()) {
                return it.toDomain()
            }
        }


        val response = api.getTrendingDay(mediaType, page)

        val trending = response.body() ?: return emptyList()

        val domain = trending.toDomain()

        dao.insertTrending(domain.toEntity())

        return domain

    }

    suspend fun clearTrending(trendingType: String) {
        dao.clearTrending(trendingType)
    }


}