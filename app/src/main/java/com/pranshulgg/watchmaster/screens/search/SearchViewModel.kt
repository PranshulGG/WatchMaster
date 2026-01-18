package com.pranshulgg.watchmaster.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repo: SearchRepository,
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {

    var query by mutableStateOf("")
        private set

    var results by mutableStateOf<List<SearchItem>>(emptyList())
        private set

    var loading by mutableStateOf(false)
        private set

    fun onQueryChange(q: String) {
        query = q
    }

    private val mockResults = listOf(
        SearchItem(
            id = 2640,
            title = "Japanese Spiderman",
            posterPath = "/w5pTlusOFWrOib6XX3iCMjHSzB1.jpg",
            mediaType = "tv",
            releaseDate = "1978-05-17",
            genreIds = listOf(10759, 10765),
            overview = "Follow the exploits of motorcycle racer turned super hero Takuya Yamashiro, as he slings and swings, battling the evil Iron Cross Army along the way."
        ),
        SearchItem(
            id = 888,
            title = "Spider-Man",
            posterPath = "/wXthtEN5kdWA1bHz03lkuCJS6hA.jpg",
            mediaType = "tv",
            releaseDate = "1994-11-19",
            genreIds = listOf(16, 10759),
            overview = ""

        ),
        SearchItem(
            id = 557,
            title = "Spider-Man",
            posterPath = "/kjdJntyBeEvqm9w97QGBdxPptzj.jpg",
            mediaType = "movie",
            releaseDate = "2002-05-01",
            genreIds = listOf(28, 878),
            overview = ""

        ),
        SearchItem(
            id = 557,
            title = "Spider-Man",
            posterPath = "/kjdJntyBeEvqm9w97QGBdxPptzj.jpg",
            mediaType = "movie",
            releaseDate = "2002-05-01",
            genreIds = listOf(28, 878),
            overview = ""

        ),
        SearchItem(
            id = 557,
            title = "Spider-Man",
            posterPath = "/kjdJntyBeEvqm9w97QGBdxPptzj.jpg",
            mediaType = "movie",
            releaseDate = "2002-05-01",
            genreIds = listOf(28, 878),
            overview = ""

        )
    )


//    init {
//        results = mockResults
//        loading = false
//    }

    fun search() {
        if (query.isBlank()) return

        viewModelScope.launch {
            loading = true
            results = repo.search(query)
            loading = false
        }
    }

    fun addToWatchlist(item: SearchItem) {
        viewModelScope.launch {
            watchlistRepository.addFromSearch(item)
        }
    }

}
