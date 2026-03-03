package com.pranshulgg.watchmaster.feature.shared.media.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.media.CastItem
import com.pranshulgg.watchmaster.core.ui.components.media.MediaSectionCard
import com.pranshulgg.watchmaster.data.local.entity.MovieBundle
import com.pranshulgg.watchmaster.data.local.entity.TvBundle


@Composable
fun CastTvSection(tvItem: TvBundle) {
    MediaSectionCard(
        title = "Cast",
        titleIcon = R.drawable.groups_2_24px,
    ) {
        val mainCast = tvItem.credits.cast
        LazyRow {
            item {
                Spacer(Modifier.width(8.dp))
            }
            items(mainCast) { castMember ->
                CastItem(
                    character = castMember.character ?: "",
                    name = castMember.name,
                    profilePath = castMember.profile_path
                )
            }
            item {
                Spacer(Modifier.width(8.dp))
            }

        }
    }
    Spacer(
        modifier = Modifier.height(12.dp)
    )
    Spacer(
        modifier = Modifier
            .windowInsetsBottomHeight(WindowInsets.navigationBars)
    )
}


@Composable
fun CastMovieSection(movieItem: MovieBundle) {
    MediaSectionCard(
        title = "Cast",
        titleIcon = R.drawable.groups_2_24px,
    ) {
        val director = movieItem.credits.crew.firstOrNull { crew ->
            crew.job == "Director"
        }

        val mainCast = movieItem.credits.cast.take(8)


        LazyRow {
            director?.let { director ->
                item {
                    Spacer(Modifier.width(8.dp))
                }
                item {
                    CastItem(
                        character = "Director",
                        name = director.name,
                        profilePath = director.profile_path
                    )

                }
            }
            items(mainCast) { castMember ->
                CastItem(
                    character = castMember.character ?: "",
                    name = castMember.name,
                    profilePath = castMember.profile_path
                )
            }
            item {
                Spacer(Modifier.width(8.dp))
            }

        }
    }
    Spacer(
        modifier = Modifier.height(12.dp)
    )
    Spacer(
        modifier = Modifier
            .windowInsetsBottomHeight(WindowInsets.navigationBars)
    )
}


