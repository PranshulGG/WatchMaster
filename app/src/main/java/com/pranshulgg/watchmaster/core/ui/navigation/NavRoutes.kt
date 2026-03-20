package com.pranshulgg.watchmaster.core.ui.navigation

import com.pranshulgg.watchmaster.feature.search.SearchType

object NavRoutes {
    const val MAIN = "main"
    const val SETTINGS = "settings"
    const val SEARCH = "search"

    const val MOVIE_LISTS_SCREEN = "movie_lists_screen"

    const val MOVIE_LISTS_CREATE_SCREEN = "movie_lists_create_screen"

    const val MOVIE_DETAIL_SCREEN = "movie_detail"
    const val TV_DETAIL_SCREEN = "tv_screen"

    fun movieDetail(id: Long): String {
        return "$MOVIE_DETAIL_SCREEN/$id"
    }

    fun tvDetail(id: Long, seasonNumber: Int, seasonId: Long): String {
        return "$TV_DETAIL_SCREEN/$id/$seasonNumber/$seasonId"
    }

    fun search(type: SearchType = SearchType.MULTI): String {
        return "$SEARCH?searchType=${type.name}"
    }
}