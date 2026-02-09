package com.pranshulgg.watchmaster.screens.media_detail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.utils.Radius
import com.pranshulgg.watchmaster.utils.Symbol

@OptIn(ExperimentalTextApi::class)
@Composable
fun SectionCard(title: String, titleIcon: Int, content: @Composable () -> Unit) {
    Card(
        shape = RoundedCornerShape(Radius.ExtraLarge),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceBright
        )
    ) {
        Column(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                Symbol(
                    titleIcon,
                    color = MaterialTheme.colorScheme.secondary
                )

                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
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
            }
            content()
        }
    }

}

