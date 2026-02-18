package com.pranshulgg.watchmaster.data

data class CreditsDto(
    val cast: List<CastMember>,
    val crew: List<CrewMember>
)

data class CastMember(
    val id: Long,
    val name: String,
    val character: String?,
    val profile_path: String?
)

data class CrewMember(
    val id: Long,
    val name: String,
    val job: String?,
    val department: String?,
    val profile_path: String?
)

data class ImagesDto(
    val backdrops: List<ImageItem>,
    val posters: List<ImageItem>
)

data class ImageItem(
    val file_path: String,
    val width: Int?,
    val height: Int?,
    val iso_639_1: String?,
    val vote_average: Float?,
    val vote_count: Int?
)

data class WatchProvidersDto(
    val results: Map<String, CountryWatchProviders>
)

data class CountryWatchProviders(
    val link: String?,
    val flatrate: List<Provider>?,
    val rent: List<Provider>?,
    val buy: List<Provider>?
)

data class Provider(
    val provider_id: Int,
    val provider_name: String,
    val logo_path: String?
)

data class MovieListDto(
    val page: Int,
    val results: List<SimpleMovie>,
    val total_pages: Int,
    val total_results: Int
)

data class SimpleMovie(
    val id: Long,
    val title: String?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val genre_ids: List<Int>?
)

data class ReviewsDto(
    val page: Int,
    val results: List<ReviewItem>,
    val total_pages: Int,
    val total_results: Int
)

data class ReviewItem(
    val id: String,
    val author: String,
    val content: String,
    val url: String
)

