package com.pranshulgg.watchmaster.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.data.repository.WatchlistRepository
import com.pranshulgg.watchmaster.model.SearchType
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
            title = "Crayon Shin-chan: Fast Asleep! Dreaming World Big Assault!",
            posterPath = "/xgHTpx8dywe3QTd5iuKuB0g3SNv.jpg",
            mediaType = "tv",
            releaseDate = "1978-05-17",
            genreIds = listOf(),
            overview = "One night, the Nohara family were enjoying a pleasant dream, when suddenly a big fish appeared in their dreams and ate them. The next morning, Hiroshi read in the newspaper that everybody in another town had the same nightmare as him, but it seemed to have ended. But Hiroshi also heard the same dream from Misae, Shinnosuke, Himawari and even Shiro. They were surprised and thought if the same thing is happening in Kasukabe too. In kindergarten, on telling others about his nightmare, Shinnosuke was surprised to know that everybody too had the same dream. Then a mysterious girl named Saki was transferred to Futaba Kindergarten and joined Shinnosuke's class. Everyone in the class, including the rather inactive Bo-chan, were all excited and happy on seeing her. But Saki had a cold attitude and didn't get along well."
        ),
        SearchItem(
            id = 888,
            title = "Spider-Man",
            posterPath = null,
            mediaType = "tv",
            releaseDate = "",
            genreIds = listOf(),
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
    )

//
//    init {
//        results = mockResults
//        loading = false
//    }

    fun search(type: SearchType = SearchType.MULTI) {
        if (query.isBlank()) return

        viewModelScope.launch {
            loading = true
            results = repo.search(query, type)
            loading = false
        }
    }


    fun addToWatchlist(item: SearchItem) {
        viewModelScope.launch {
            watchlistRepository.addFromSearch(item)
        }
    }

}
