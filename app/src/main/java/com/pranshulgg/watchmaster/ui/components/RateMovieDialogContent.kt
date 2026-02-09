package com.pranshulgg.watchmaster.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalSlider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.ui.theme.RobotoFlexWide
import com.pranshulgg.watchmaster.utils.Radius
import kotlin.math.roundToInt


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalTextApi::class
)
@Composable
fun RateMovieDialogContent(
    onConfirm: (Double) -> Unit,
    onCancel: () -> Unit,
    updateRating: Boolean = false,
    originalRating: Float = 0f
) {

    val sliderState = remember {
        SliderState(
            value = if (updateRating) originalRating else 0f,
            valueRange = 0f..10f,
            steps = 19,
        )
    }
    val rating = (sliderState.value * 10).roundToInt() / 10f

    val displayRating =
        if (rating == 10f) "10" else "%.1f".format(rating)


    Row(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {


        Column {
            Text(
                displayRating, fontSize = 100.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.roboto_flex,
                        variationSettings = FontVariation.Settings(
                            FontVariation.width(150f),
                            FontVariation.weight(1000)
                        )
                    )
                ),
                color = MaterialTheme.colorScheme.secondary
            )
            Box(
                modifier = Modifier
                    .height(3.dp)
                    .width(150.dp)
                    .background(color = MaterialTheme.colorScheme.outlineVariant),
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "10",
                fontSize = 34.sp,
                fontFamily = RobotoFlexWide,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

        }
        VerticalSlider(
            state = sliderState,
            track = {
                SliderDefaults.Track(
                    sliderState = sliderState,

                    modifier =
                        Modifier
                            .width(36.dp)
                            .drawWithContent {
                                drawContent()
                            },
                    trackCornerSize = Radius.Medium,
                )
            },
            reverseDirection = true
        )

    }


    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(top = 12.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.35f),
            onClick = {
                onCancel()
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer),
            shapes = ButtonDefaults.shapes()
        ) {
            Text("Cancel", color = MaterialTheme.colorScheme.onErrorContainer)
        }
        Button(
            enabled = rating > 0f,
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onCancel()
                onConfirm(rating.toDouble())
            },
            shapes = ButtonDefaults.shapes()
        ) {
            Text(if (updateRating) "Update" else "Mark as finished")
        }

    }
}