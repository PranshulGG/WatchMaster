package com.pranshulgg.watchmaster.ui.snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SnackbarManager {

    private val _messages = MutableSharedFlow<String>(
        extraBufferCapacity = 1
    )
    val messages = _messages.asSharedFlow()

    fun show(message: String) {
        _messages.tryEmit(message)
    }
}