package com.pranshulgg.watchmaster.network

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


interface TmdbApi {
    @GET
    suspend fun search(
        @Url url: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US"
    ): Response<MultiSearchResponse>

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
