package com.pranshulgg.watchmaster.data

data class Genre(
    val id: Int,
    val name: String
)

val LOCAL_GENRES = listOf(
    Genre(28, "Action"),
    Genre(12, "Adventure"),
    Genre(10759, "Action & Adventure"),

    Genre(16, "Animation"),
    Genre(35, "Comedy"),
    Genre(80, "Crime"),
    Genre(99, "Documentary"),
    Genre(18, "Drama"),

    Genre(10751, "Family"),
    Genre(10762, "Kids"),

    Genre(14, "Fantasy"),
    Genre(10765, "Sci-Fi & Fantasy"),
    Genre(878, "Science Fiction"),

    Genre(36, "History"),
    Genre(27, "Horror"),
    Genre(10402, "Music"),

    Genre(9648, "Mystery"),
    Genre(10749, "Romance"),

    Genre(53, "Thriller"),
    Genre(10752, "War"),
    Genre(10768, "War & Politics"),

    Genre(37, "Western"),

    Genre(10763, "News"),
    Genre(10764, "Reality"),
    Genre(10766, "Soap"),
    Genre(10767, "Talk"),

    Genre(10770, "TV Movie")
)

val GENRE_MAP: Map<Int, String> =
    LOCAL_GENRES.associate { it.id to it.name }

fun getGenreNames(genreIds: List<Int>): List<String> {
    return genreIds.mapNotNull { GENRE_MAP[it] }
}
