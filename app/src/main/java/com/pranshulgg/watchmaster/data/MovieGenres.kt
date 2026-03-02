package com.pranshulgg.watchmaster.data

data class MovieGenre(
    val id: Int,
    val name: String
)

val LOCAL_MOVIE_GENRES = listOf(
    MovieGenre(28, "Action"),
    MovieGenre(12, "Adventure"),
    MovieGenre(10759, "Action & Adventure"),

    MovieGenre(16, "Animation"),
    MovieGenre(35, "Comedy"),
    MovieGenre(80, "Crime"),
    MovieGenre(99, "Documentary"),
    MovieGenre(18, "Drama"),

    MovieGenre(10751, "Family"),
    MovieGenre(10762, "Kids"),

    MovieGenre(14, "Fantasy"),
    MovieGenre(10765, "Sci-Fi & Fantasy"),
    MovieGenre(878, "Science Fiction"),

    MovieGenre(36, "History"),
    MovieGenre(27, "Horror"),
    MovieGenre(10402, "Music"),

    MovieGenre(9648, "Mystery"),
    MovieGenre(10749, "Romance"),

    MovieGenre(53, "Thriller"),
    MovieGenre(10752, "War"),
    MovieGenre(10768, "War & Politics"),

    MovieGenre(37, "Western"),

    MovieGenre(10763, "News"),
    MovieGenre(10764, "Reality"),
    MovieGenre(10766, "Soap"),
    MovieGenre(10767, "Talk"),

    MovieGenre(10770, "TV Movie")
)

val GENRE_MAP: Map<Int, String> =
    LOCAL_MOVIE_GENRES.associate { it.id to it.name }

fun getMovieGenreNames(genreIds: List<Int>): List<String> {
    return genreIds.mapNotNull { GENRE_MAP[it] }
}
