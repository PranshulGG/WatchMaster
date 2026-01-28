package com.pranshulgg.watchmaster.helpers

import com.pranshulgg.watchmaster.model.SearchType

object NavRoutes {
    const val MAIN = "main"
    const val SETTINGS = "settings"
    const val SEARCH = "search"

    const val MEDIA_DETAIL_PAGE = "mediadetail"

    fun mediaDetail(id: Long): String {
        return "$MEDIA_DETAIL_PAGE/$id"
    }

    fun search(type: SearchType = SearchType.MULTI): String {
        return "$SEARCH?type=${type.name}"
    }
}
