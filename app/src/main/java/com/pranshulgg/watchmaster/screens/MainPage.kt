package com.pranshulgg.watchmaster.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.models.HomeNavViewModel
import com.pranshulgg.watchmaster.ui.components.BottomNav

@OptIn(ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: HomeNavViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {

    val selectedItem = viewModel.selectedItem
    val appBarTitles = listOf("Home", "Movies", "TV series")
    val context = LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(appBarTitles[selectedItem])
                },
            )
        },
        bottomBar = {
            BottomNav(
                selectedItem = selectedItem,
                onItemSelected = { index -> viewModel.selectedItem = index }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (selectedItem) {
                0 -> HomeScreen()
            }

        }
    }


}