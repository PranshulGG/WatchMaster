package com.pranshulgg.watchmaster.feature.shared.media.ui

data class FloatingToolbarMediaActionsParams(
    val startWatching: () -> Unit,
    val resetWatching: () -> Unit,
    val finishWatching: () -> Unit,
    val interruptWatching: () -> Unit,
    val delete: () -> Unit,
    val togglePin: () -> Unit,
)
