package com.pranshulgg.watchmaster.feature.tv.detail.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchProviderSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    viewModel: TvDetailsViewModel
) {

    val providers = viewModel.uiState.value.currentWatchProviders

    if (viewModel.uiState.value.isWatchProviderSheetOpen)
        ActionBottomSheet(
            sheetState = sheetState,
            onCancel = { onDismiss() },
            onConfirm = { onConfirm() },
            confirmBtnMaxWidth = true
        ) {

            if (providers != null && providers.buy != null) {
                providers.buy.forEach {
                    Text(it.provider_name)
                }
            }

        }

}