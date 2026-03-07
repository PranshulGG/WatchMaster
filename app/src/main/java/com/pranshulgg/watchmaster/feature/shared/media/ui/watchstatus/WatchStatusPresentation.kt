package com.pranshulgg.watchmaster.feature.shared.media.ui.watchstatus

import com.pranshulgg.watchmaster.core.model.WatchStatus
import com.pranshulgg.watchmaster.R

fun WatchStatus.dialogMessage(isTv: Boolean = false): String {
    val tvMovieText: String = if (isTv) "season" else "movie";

    return when (this) {

        WatchStatus.WATCHING ->
            "Are you sure you want to finish this $tvMovieText?"

        WatchStatus.INTERRUPTED ->
            "Do you want to continue watching this $tvMovieText?"

        WatchStatus.FINISHED ->
            "Reset this $tvMovieText and add it back to your watchlist?"

        else ->
            "Are you sure you want to start watching this $tvMovieText?"
    }
}

fun WatchStatus.confirmAction(
    start: () -> Unit,
    reset: () -> Unit,
    finish: () -> Unit,
) {
    when (this) {
        WatchStatus.WATCHING -> finish()
        WatchStatus.FINISHED -> reset()
        else -> start()
    }
}

val WatchStatus.actionLabel: String
    get() = when (this) {
        WatchStatus.WATCHING -> "Mark as finished"
        WatchStatus.INTERRUPTED -> "Continue watching"
        WatchStatus.FINISHED -> "Reset"
        else -> "Mark as watching"
    }

val WatchStatus.buttonIcon: Int
    get() = when (this) {
        WatchStatus.WATCHING -> R.drawable.check_24px
        WatchStatus.FINISHED -> R.drawable.restart_alt_24px
        else -> R.drawable.play_arrow_24px
    }
