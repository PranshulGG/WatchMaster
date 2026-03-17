package com.pranshulgg.watchmaster.feature.tv.detail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.core.ui.components.ActionBottomSheet
import com.pranshulgg.watchmaster.core.ui.components.LoadingPlaceholder
import com.pranshulgg.watchmaster.core.ui.components.LoadingScreenPlaceholder
import com.pranshulgg.watchmaster.feature.shared.media.components.WatchProviderItem
import com.pranshulgg.watchmaster.feature.tv.detail.TvDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchProviderSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    viewModel: TvDetailsViewModel
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
            }
        }

}