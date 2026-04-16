package com.pranshulgg.watchmaster.feature.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.core.model.Trending
import com.pranshulgg.watchmaster.data.repository.TrendingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val trendingRepo: TrendingRepository
) : ViewModel() {

    var trendingDay by mutableStateOf<List<Trending>>(emptyList())
        private set

    fun getTrending(mediaType: String, page: Int, trendingType: String) {
        if (trendingDay.isNotEmpty()) return


        viewModelScope.launch {
            val trending = try {
                trendingRepo.fetchTrending(mediaType, page, trendingType)
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                return@launch
            }


            trendingDay = trending
        }
    }

    fun clearTrending(trendingType: String) {
        viewModelScope.launch {
            trendingRepo.clearTrending(trendingType)
        }
    }

}