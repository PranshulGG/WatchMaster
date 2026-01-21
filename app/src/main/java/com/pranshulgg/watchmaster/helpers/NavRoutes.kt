package com.pranshulgg.watchmaster.helpers

import com.pranshulgg.watchmaster.model.SearchType

object NavRoutes {
    const val MAIN = "main"
    const val SETTINGS = "settings"
    const val SEARCH = "search"

    fun search(type: SearchType = SearchType.MULTI): String {
        return "$SEARCH?type=${type.name}"
    }
}
