package com.pranshulgg.watchmaster.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import com.pranshulgg.watchmaster.core.network.TvSeasonDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: SearchRepository,
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {

    var query by mutableStateOf("")
        private set

    var results by mutableStateOf<List<SearchItem>>(emptyList())
        private set

    var loading by mutableStateOf(false)
        private set

    var showNoResultsSnack by mutableStateOf(false)
        private set

    var seasonData by mutableStateOf<List<TvSeasonDto>>(emptyList())
        private set

    var seasonLoading by mutableStateOf(false)
        private set


    fun onQueryChange(q: String) {
        query = q
    }

    private val seasonCache = mutableMapOf<Long, List<TvSeasonDto>>()


    private val seasonMockData = listOf(
        TvSeasonDto(
            id = 1,
            name = "Season 1",
            season_number = 1,
            episode_count = 22,
            poster_path = "/3z2mYFxUkzanb2eeIcVyfJq0G3q.jpg",
            air_date = "",
        ),
        TvSeasonDto(
            id = 2,
            name = "Season 2",
            season_number = 2,
            episode_count = 25,
            poster_path = "/3z2mYFxUkzanb2eeIcVyfJq0G3q.jpg",
            air_date = "",
        ),
        TvSeasonDto(
            id = 5,
            name = "Season 3",
            season_number = 3,
            episode_count = 15,
            poster_path = "/3z2mYFxUkzanb2eeIcVyfJq0G3q.jpg",
            air_date = "",
        )
    )

    private val mockResults = listOf(
        SearchItem(
            id = 243875,
            title = "Georgie & Mandy's First Marriage",
            posterPath = "/3z2mYFxUkzanb2eeIcVyfJq0G3q.jpg",
            mediaType = "tv",
            releaseDate = "2024-10-17",
            genreIds = listOf(35),
            overview = "Georgie and Mandy raise their young family in Texas while navigating the challenges of adulthood, parenting, and marriage.",
            backdropPath = "/pCr3YYJkkLt9fdGVD7I2Z1l6lzK.jpg"
        ),
        SearchItem(
            id = 12,
            title = "Finding Nemo",
            posterPath = "/eHuGQ10FUzK1mdOY69wF5pGgEf5.jpg",
            mediaType = "movie",
            releaseDate = "2003-05-30",
            genreIds = listOf(16, 10751),
            overview = "Nemo, an adventurous young clownfish, is unexpectedly taken from his Great Barrier Reef home to a dentist's office aquarium. It's up to his worrisome father Marlin and a friendly but forgetful fish Dory to bring Nemo home -- meeting vegetarian sharks, surfer dude turtles, hypnotic jellyfish, hungry seagulls, and more along the way.",
            backdropPath = ""

        ),
        SearchItem(
            id = 888,
            title = "Spider-Man",
            posterPath = null,
            mediaType = "tv",
            releaseDate = "",
            genreIds = listOf(),
            overview = "",
            backdropPath = ""


        ),
        SearchItem(
            id = 557,
            title = "Spider-Man",
            posterPath = "/kjdJntyBeEvqm9w97QGBdxPptzj.jpg",
            mediaType = "movie",
            releaseDate = "2002-05-01",
            genreIds = listOf(28, 878),
            overview = "",
            backdropPath = ""

        ),
        SearchItem(
            id = 557,
            title = "Spider-Man",
            posterPath = "/kjdJntyBeEvqm9w97QGBdxPptzj.jpg",
            mediaType = "movie",
            releaseDate = "2002-05-01",
            genreIds = listOf(28, 878),
            overview = "",
            backdropPath = ""

        ),
    )

//    init {
//        results = mockResults
//        loading = false
//    }

    fun search(type: SearchType = SearchType.MULTI) {
        if (query.isBlank()) return

        viewModelScope.launch {
            loading = true
            val res = repo.search(query, type)
            results = res
            loading = false

            showNoResultsSnack = res.isEmpty()
        }
    }


    fun fetchSeasonData(tvId: Long) {
        seasonCache[tvId]?.let {
            seasonData = it
            return
        }

        viewModelScope.launch {
            seasonLoading = true
            val data = repo.getSeasonData(tvId)
            seasonCache[tvId] = data
            seasonData = data
            seasonLoading = false
        }
    }

    fun addToWatchlist(item: SearchItem, tvDetails: List<TvSeasonDto>? = null) {
        viewModelScope.launch {
            watchlistRepository.addFromSearch(item, tvDetails)
        }
    }

}
