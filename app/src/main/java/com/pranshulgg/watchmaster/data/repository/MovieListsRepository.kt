package com.pranshulgg.watchmaster.data.repository

import com.pranshulgg.watchmaster.core.model.MediaListsIcons
import com.pranshulgg.watchmaster.data.local.dao.MovieListsDao
import com.pranshulgg.watchmaster.data.local.entity.MovieListsEntity

class MovieListsRepository(
    private val dao: MovieListsDao
) {

    fun getMovieLists() = dao.getMovieLists()

    suspend fun insertMovieListsItem(item: MovieListsEntity) = dao.insertMovieListItem(item)

    suspend fun deleteMovieListsItem(id: Long) = dao.deleteMovieListsItem(id)

    suspend fun updateListIcon(id: Long, icon: MediaListsIcons) = dao.updateListIcon(id, icon)

}