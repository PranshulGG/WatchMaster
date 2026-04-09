package com.pranshulgg.watchmaster.feature.tv.detail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pranshulgg.watchmaster.feature.shared.WatchlistViewModel
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsViewModel

@Composable
fun TvDetailEffects(
    id: Long,
    seasonNumber: Int,
    viewModel: TvDetailsViewModel,
    seasonId: Long
) {
    LaunchedEffect(id) { viewModel.load(id, onBack = {}) }
    LaunchedEffect(seasonNumber) { viewModel.loadEpisodes(id, seasonId, seasonNumber) }
    LaunchedEffect(Unit) { viewModel.loading }

}