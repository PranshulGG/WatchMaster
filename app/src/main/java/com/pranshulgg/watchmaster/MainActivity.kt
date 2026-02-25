package com.pranshulgg.watchmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import com.pranshulgg.watchmaster.core.prefs.AppPrefs.initPrefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        initPrefs(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WatchMasterApp()
        }
    }
}


