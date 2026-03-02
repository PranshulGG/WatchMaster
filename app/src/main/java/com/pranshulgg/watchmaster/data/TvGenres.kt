package com.pranshulgg.watchmaster.data

data class TvGenre(
    val id: Int,
    val name: String
)

val LOCAL_TV_GENRES = listOf(
    TvGenre(10759, "Action & Adventure"),
    TvGenre(16, "Animation"),
    TvGenre(35, "Comedy"),
    TvGenre(80, "Crime"),
    TvGenre(99, "Documentary"),
    TvGenre(18, "Drama"),
    TvGenre(10751, "Family"),
    TvGenre(10762, "Kids"),
    TvGenre(9648, "Mystery"),
    TvGenre(10763, "News"),
    TvGenre(10764, "Reality"),
    TvGenre(10765, "Sci-Fi & Fantasy"),
    TvGenre(10766, "Soap"),
    TvGenre(10767, "Talk"),
    TvGenre(10768, "War & Politics"),
    TvGenre(37, "Western")
)

val TV_GENRE_MAP: Map<Int, String> =
    LOCAL_TV_GENRES.associate { it.id to it.name }

fun getTvGenreNames(genreIds: List<Int>): List<String> {
    return genreIds.mapNotNull { TV_GENRE_MAP[it] }
}
