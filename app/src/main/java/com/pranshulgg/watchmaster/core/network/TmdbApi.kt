package com.pranshulgg.watchmaster.core.network

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import com.pranshulgg.watchmaster.BuildConfig
import com.pranshulgg.watchmaster.data.CreditsDto
import com.pranshulgg.watchmaster.data.EpisodeItem
import com.pranshulgg.watchmaster.data.EpisodeListDto
import com.pranshulgg.watchmaster.data.ImagesDto
import com.pranshulgg.watchmaster.data.MovieGenre
import com.pranshulgg.watchmaster.data.MovieListDto
import com.pranshulgg.watchmaster.data.ReviewsDto
import com.pranshulgg.watchmaster.data.TvCreditsDto
import com.pranshulgg.watchmaster.data.TvGenre
import com.pranshulgg.watchmaster.data.TvReviewsDto
import com.pranshulgg.watchmaster.data.TvWatchProvidersDto
import com.pranshulgg.watchmaster.data.WatchProvidersDto
import retrofit2.http.Path
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

data class MultiSearchResponse(
    val page: Int,
    val results: List<TmdbResult>,
    val total_pages: Int,
    val total_results: Int
)

data class TmdbResult(
    val id: Long,
    val media_type: String?,
    val title: String?,
    val name: String?,
    val overview: String?,
    val poster_path: String?,
    val profile_path: String?,
    val known_for: List<KnownFor>?,
    val backdrop_path: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,

    val vote_average: Double,
)

data class KnownFor(
    val title: String?,
    val name: String?,
    val poster_path: String?,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("genre_ids")
    val genreIds: List<Int>?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("first_air_date")
    val firstAirDate: String?
)

data class MovieBundleDto(
    val id: Long,
    val title: String,
    val overview: String,
    val runtime: Int?,
    val genres: List<MovieGenre>,

    val credits: CreditsDto,
    val images: ImagesDto,
    val poster_path: String?,
    val backdrop_path: String?,


    @SerializedName("watch/providers")
    val watchProviders: WatchProvidersDto?,

    val similar: MovieListDto,
    val recommendations: MovieListDto,
    val reviews: ReviewsDto,
)

data class TvBundleDto(
    val id: Long,
    val name: String,
    val overview: String,
    val runtime: Int?,
    val poster_path: String?,
    val backdrop_path: String?,
    val genres: List<TvGenre>,
    val season_number: Int,
    val credits: TvCreditsDto,
    @SerializedName("watch/providers")
    val watchProviders: TvWatchProvidersDto?,
    val reviews: TvReviewsDto,
//    val episodes: List<EpisodeItem>
)

data class TvSeasonsResponse(
    val id: Long,
    val seasons: List<TvSeasonDto>
)

data class TvSeasonDto(
    val id: Long,
    val name: String,
    val season_number: Int,
    val episode_count: Int,
    val poster_path: String?,
    val air_date: String?,
    val vote_average: Double?

)


interface TmdbApi {
    @GET
    suspend fun search(
        @Url url: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US"
    ): Response<MultiSearchResponse>

    @GET("tv/{tv_id}")
    suspend fun getTvSeasons(
        @Path("tv_id") tvId: Long,
        @Query("language") language: String = "en-US"
    ): Response<TvSeasonsResponse>


    @GET("movie/{movie_id}")
    suspend fun getWholeMovieData(
        @Path("movie_id") movieId: Long,
        @Query("append_to_response") append: String =
            "credits,videos,images,watch/providers,similar,recommendations,reviews,release_dates",
        @Query("language") language: String = "en-US"
    ): Response<MovieBundleDto>


    @GET("tv/{tv_id}")
    suspend fun getWholeTvData(
        @Path("tv_id") tvId: Long,
//        @Path("season_number") seasonNumber: Int,
        @Query("append_to_response") append: String =
            "credits,videos,images,watch/providers,similar,recommendations,reviews,content_ratings,external_ids",
        @Query("language") language: String = "en-US"
    ): Response<TvBundleDto>

    companion object {
        private const val BASE = "https://api.themoviedb.org/3/"

        fun create(): TmdbApi {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val auth = Interceptor { chain ->
                val original = chain.request()
                val newUrl = original.url.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                    .build()
                val request = original.newBuilder().url(newUrl).build()
                chain.proceed(request)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(auth)
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TmdbApi::class.java)
        }
    }
}
