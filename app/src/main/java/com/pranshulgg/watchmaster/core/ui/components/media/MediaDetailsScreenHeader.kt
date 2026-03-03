package com.pranshulgg.watchmaster.core.ui.components.media

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.pranshulgg.watchmaster.core.ui.components.DialogBasic
import com.pranshulgg.watchmaster.core.ui.components.Symbol

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MediaDetailsScreenHeader(
    navController: NavController,
    isFinished: Boolean,
    userRating: Double?,
    seasonUserRating: Double? = 0.0,
    onUpdateRating: (Double) -> Unit,
    isTv: Boolean = false,
) {
    val schemeColor = MaterialTheme.colorScheme
    var showRatingDialog by remember { mutableStateOf(false) }
    TopAppBar(
        modifier = Modifier.zIndex(2f),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            FilledIconButton(
                onClick = { navController.popBackStack() }, shapes = IconButtonDefaults.shapes()
            ) {
                Symbol(
                    R.drawable.arrow_back_24px,
                    desc = "settings icon",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        title = {
        },
        actions = {
            if (isFinished) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = schemeColor.inverseSurface,
                        shape = CircleShape
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 12.dp, end = 14.dp)
                                .height(40.dp)
                        ) {
                            Symbol(
                                R.drawable.star_24px,
                                size = 24.dp,
                                color = schemeColor.inverseOnSurface
                            )
                            Spacer(Modifier.width(3.dp))
                            Text(
                                if (userRating == 10.0) "10" else userRating.toString(),
                                color = schemeColor.inverseOnSurface,
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
            Spacer(Modifier.width(8.dp))
        }
    )


    DialogBasic(
        show = showRatingDialog,
        title = "Update rating",
        showDefaultActions = false,
        onDismiss = { showRatingDialog = false },
        content = {
            RateMediaDialogContent(
                onCancel = { showRatingDialog = false },
                updateRating = true,
                originalRating = if (isTv) seasonUserRating?.toFloat()
                    ?: 0f else userRating?.toFloat() ?: 0f,
                onConfirm = { rating ->
                    onUpdateRating(rating)
                }
            )
        }
    )
}