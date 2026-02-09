package com.pranshulgg.watchmaster.screens.media_detail.ui

import android.media.Rating
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.ui.components.DialogBasic
import com.pranshulgg.watchmaster.ui.components.RateMovieDialogContent
import com.pranshulgg.watchmaster.utils.Radius
import com.pranshulgg.watchmaster.utils.Symbol
import com.pranshulgg.watchmaster.utils.topSysStatusPadding

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TopNavMovieDetailsScreenHeader(
    navController: NavController,
    isFinished: Boolean,
    userRating: Double?,
    onUpdateRating: (Double) -> Unit
) {
    val schemeColor = MaterialTheme.colorScheme
    var showRatingDialog by remember { mutableStateOf(false) }

    Row(
        Modifier
            .padding(top = topSysStatusPadding(), start = 8.dp, end = 12.dp)
            .fillMaxWidth()
            .zIndex(2f),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledIconButton(
            onClick = { navController.popBackStack() }, shapes = IconButtonDefaults.shapes()
        ) {
            Symbol(
                R.drawable.arrow_back_24px,
                desc = "settings icon",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        if (isFinished) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    color = schemeColor.surface,
                    shape = CircleShape
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 12.dp, end = 14.dp)
                            .height(40.dp)
                    ) {
                        Symbol(R.drawable.star_24px, size = 24.dp, color = schemeColor.onSurface)
                        Spacer(Modifier.width(3.dp))
                        Text(
                            userRating.toString(),
                            color = schemeColor.onSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(Modifier.width(5.dp))
                FilledIconButton(
                    modifier = Modifier.width(36.dp),
                    onClick = {
                        showRatingDialog = true
                    }, shapes = IconButtonDefaults.shapes()
                ) {
                    Symbol(
                        R.drawable.edit_24px,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

    }


    DialogBasic(
        show = showRatingDialog,
        title = "Update rating",
        showDefaultActions = false,
        onDismiss = { showRatingDialog = false },
        content = {
            RateMovieDialogContent(
                onCancel = { showRatingDialog = false },
                updateRating = true,
                originalRating = userRating?.toFloat() ?: 0f,
                onConfirm = { rating ->
                    onUpdateRating(rating)
                }
            )
        }
    )
}