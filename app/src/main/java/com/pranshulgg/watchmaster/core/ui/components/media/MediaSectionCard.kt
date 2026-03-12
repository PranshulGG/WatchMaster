package com.pranshulgg.watchmaster.core.ui.components.media

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButton
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
import com.pranshulgg.watchmaster.core.ui.theme.Radius
import com.pranshulgg.watchmaster.core.ui.components.Symbol

@OptIn(ExperimentalTextApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MediaSectionCard(
    title: String,
    titleIcon: Int,
    showAction: Boolean = false,
    actionText: String = "",
    actionOnClick: () -> Unit = {},
    noPadding: Boolean = false,
    content: @Composable () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = if (noPadding) 0.dp else 16.dp),
        shape = RoundedCornerShape(Radius.ExtraLarge),
    ) {
        Column(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    5.dp,
                    alignment = Alignment.CenterHorizontally
                ),
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

                if (showAction) {
                    Spacer(Modifier.weight(1f))
                    if (actionText != "") {
                        HeaderAction(text = actionText, onClick = { actionOnClick() })
                    }
                }


            }
            content()
        }
    }

}


@Composable
private fun HeaderAction(text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(
                onClick = {
                    onClick()
                }
            )
            .padding(end = 5.dp)
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
