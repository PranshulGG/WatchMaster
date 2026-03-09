package com.pranshulgg.watchmaster.feature.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.feature.search.SearchType
import com.pranshulgg.watchmaster.feature.search.SearchViewModel
import com.pranshulgg.watchmaster.feature.search.components.SearchFloatingBarContent


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SearchFloatingToolbar(
    viewModel: SearchViewModel,
    searchType: SearchType,
    scrollBehaviorToolbar: FloatingToolbarScrollBehavior,
    query: String
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val systemInsets = WindowInsets.systemBars.asPaddingValues()

    Box(
        Modifier
            .fillMaxWidth()
            .imePadding()
    ) {

        HorizontalFloatingToolbar(
            scrollBehavior = scrollBehaviorToolbar,
            contentPadding = PaddingValues(top = 0.dp, bottom = 0.dp, start = 10.dp),
            modifier = Modifier
                .padding(
                    bottom = systemInsets.calculateBottomPadding() + ScreenOffset,
                    top = ScreenOffset
                )
                .height(64.dp)
                .align(Alignment.BottomCenter)
                .zIndex(1f),
            colors = FloatingToolbarDefaults.vibrantFloatingToolbarColors(),
            expanded = true,
            floatingActionButton = {
                FloatingToolbarDefaults.VibrantFloatingActionButton(
                    onClick = {
                        viewModel.search(searchType)
                        focusManager.clearFocus()
                    }
                ) {
                    Symbol(
                        R.drawable.search_24px,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            },
            content = {
                SearchFloatingBarContent(
                    viewModel,
                    query,
                    focusRequester,
                    focusManager,
                    searchType
                )
            }
        )
    }
}
