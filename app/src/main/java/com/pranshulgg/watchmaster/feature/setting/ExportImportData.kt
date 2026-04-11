package com.pranshulgg.watchmaster.feature.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.room.withTransaction
import com.google.gson.Gson
import com.pranshulgg.watchmaster.core.ui.snackbar.SnackbarManager
import com.pranshulgg.watchmaster.data.local.WatchMasterDatabase
import com.pranshulgg.watchmaster.data.local.entity.CustomListEntity
import com.pranshulgg.watchmaster.data.local.entity.SeasonEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

private data class ExportData(
    val version: Int,
    val watchlist: List<WatchlistItemEntity>,
    val tvSeasons: List<SeasonEntity>,
    val movieLists: List<CustomListEntity>
)

@Composable
fun exportLauncher(
    context: Context,
    exportWatchlist: Boolean = false,
    exportMovieList: Boolean = false
): (Intent) -> Unit {
    val scope = rememberCoroutineScope()


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data
        uri?.let {
            scope.launch {
                export(context, uri, exportWatchlist, exportMovieList)
            }
        }
    }

    return { intent ->
        launcher.launch(intent)
    }
}

@Composable
fun importLauncher(context: Context): (Intent) -> Unit {
    val scope = rememberCoroutineScope()


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data
        uri?.let {
            scope.launch {
                import(context, uri)
            }
        }
    }

    return { intent ->
        launcher.launch(intent)
    }
}

private suspend fun export(
    context: Context,
    uri: Uri,
    exportWatchlist: Boolean = false,
    exportMovieList: Boolean = false
) {

    val db = WatchMasterDatabase.getInstance(context)
    val watchlistData = if (exportWatchlist) {
        db.watchlistDao().getAll().first()
    } else emptyList()
    val tvSeasons = if (exportWatchlist) {
        db.seasonDao().getAllSeasons().first()
    } else emptyList()
    val movieLists = if (exportMovieList) {
        db.movieListsDao().getAllCustomLists().first()
    } else emptyList()

    val json = Gson().toJson(ExportData(4, watchlistData, tvSeasons, movieLists))

    val file = context.contentResolver.openOutputStream(uri)

    withContext(Dispatchers.IO) {
        file?.write(json.toByteArray())
        file?.close()
    }

    SnackbarManager.show("Exported successfully")

}

private suspend fun import(context: Context, uri: Uri) {

    val db = WatchMasterDatabase.getInstance(context)

    val input = context.contentResolver.openInputStream(uri)
    val json = input?.bufferedReader().use { it?.readText() }

    val data = Gson().fromJson(json, ExportData::class.java)


    if (data.watchlist.isEmpty() && data.movieLists.isEmpty()) {
        SnackbarManager.show("No data to import")
        return
    }

    val unsupportedVersions = listOf(0, 1, 2, 3)

    if (unsupportedVersions.contains(data.version)) {
        SnackbarManager.show("Unsupported version")
        return
    }

    db.withTransaction {
        if (data.watchlist.isNotEmpty()) {
            db.watchlistDao().clearAll()
            db.seasonDao().clearAll()
        }

        if (data.movieLists.isNotEmpty()) {
            db.movieListsDao().clearAll()
        }

        if (data.watchlist.isNotEmpty()) {
            db.watchlistDao().insertAll(data.watchlist)
            db.seasonDao().insertSeasons(data.tvSeasons)
        }


        if (data.movieLists.isNotEmpty()) {
            db.movieListsDao().insertAll(data.movieLists)
        }
    }

    SnackbarManager.show("Imported successfully")
}


fun createNewDocumentIntent(): Intent {
    val randomId = (100000..999999).random()
    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/json"
        putExtra(Intent.EXTRA_TITLE, "watchmaster_backup-${randomId}-v4.json")
    }

    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
            Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION or
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION

    return intent
}


fun createOpenDocumentIntent(): Intent {

    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/json"
    }

    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
            Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION or
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION


    return intent
}
