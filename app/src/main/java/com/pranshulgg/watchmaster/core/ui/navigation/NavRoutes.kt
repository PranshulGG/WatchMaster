package com.pranshulgg.watchmaster.core.ui.navigation

import com.pranshulgg.watchmaster.feature.search.SearchType

object NavRoutes {
    const val MAIN = "main"
    const val SETTINGS = "settings"
    const val SEARCH = "search"

    const val LISTS_SCREEN = "lists_screen"

    const val LISTS_ENTRY_SCREEN = "lists_entry_screen"

    const val LISTS_VIEW_SCREEN = "lists_view_screen"

    const val MOVIE_DETAIL_SCREEN = "movie_detail"
    const val TV_DETAIL_SCREEN = "tv_screen"

    fun movieDetail(id: Long): String {
        return "$MOVIE_DETAIL_SCREEN/$id"
    }

    fun viewListScreen(id: Long): String {
        return "$LISTS_VIEW_SCREEN/$id"
    }

    fun tvDetail(id: Long, seasonNumber: Int, seasonId: Long): String {
        return "$TV_DETAIL_SCREEN/$id/$seasonNumber/$seasonId"
    }

    fun search(type: SearchType = SearchType.MULTI): String {
        return "$SEARCH?searchType=${type.name}"
    }

    fun listEntryScreen(id: Long): String {
        return "$LISTS_ENTRY_SCREEN/$id"
    }
}