package com.pranshulgg.watchmaster.feature.movie.detail.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.core.ui.components.EmptyContainerPlaceholder
import com.pranshulgg.watchmaster.feature.movie.detail.MovieDetailsViewModel
import com.pranshulgg.watchmaster.feature.shared.media.components.WatchProviderItem
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieWatchProviderSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    viewModel: MovieDetailsViewModel
) {

    val providers = viewModel.uiState.value.currentWatchProviders

    if (viewModel.uiState.value.isWatchProviderSheetOpen)
        ActionBottomSheet(
            sheetState = sheetState,
            onCancel = { onDismiss() },
            onConfirm = { },
            confirmBtnMaxWidth = true,
            hideConfirmBtn = true,
            cancelText = "OK"
        ) {
            if (providers != null) {

                if (providers.flatrate != null) {
                    WatchProviderItem("Flat rate", providers.flatrate)
                }

                if (providers.buy != null) {
                    Spacer(Modifier.height(12.dp))
                    WatchProviderItem("Buy", providers.buy)
                }

                Spacer(Modifier.height(10.dp))
            } else {
                EmptyContainerPlaceholder(
                    R.drawable.movie_info_24px,
                    "No providers found",
                    size = 0.7f,
                    fraction = 0.3f
                )
            }
        }

}