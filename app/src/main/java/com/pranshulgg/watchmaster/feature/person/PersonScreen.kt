package com.pranshulgg.watchmaster.feature.person

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.ui.components.ErrorContainer
import com.pranshulgg.watchmaster.core.ui.components.Gap
import com.pranshulgg.watchmaster.core.ui.components.LargeTopBarScaffold
import com.pranshulgg.watchmaster.core.ui.components.LoadingScreenPlaceholder
import com.pranshulgg.watchmaster.core.ui.components.NavigateUpBtn
import com.pranshulgg.watchmaster.core.ui.components.Symbol
import com.pranshulgg.watchmaster.core.ui.components.media.MediaChip
import com.pranshulgg.watchmaster.core.ui.components.media.MediaSectionCard
import com.pranshulgg.watchmaster.core.ui.components.media.PosterBox
import com.pranshulgg.watchmaster.core.ui.theme.ShapeRadius


@Composable
fun PersonScreen(id: Long, navController: NavController) {

    val viewModel: PersonViewModel = hiltViewModel()
    val data = viewModel.personData
    val loading = viewModel.loading

    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        viewModel.fetchPersonData(id, onError = { isError = true })
    }


    if (isError) {
        ErrorContainer(
            onRetry = {
                isError = false
                viewModel.fetchPersonData(id, onError = { isError = true })
            },
            errorDescription = "Failed to fetch person data"
        )
    }

    if (loading) {
        LoadingScreenPlaceholder()
    }

    if (data != null)

        LargeTopBarScaffold(
            title = "Person",
            navigationIcon = {
                NavigateUpBtn(navController)
            },
            defaultCollapsed = true
        ) { paddingValues ->
            LazyColumn(
                Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    PosterBox(
                        posterUrl = "https://image.tmdb.org/t/p/original${data.profilePath}",
                        apiPath = data.profilePath,
                        width = 200.dp,
                        height = 200.dp,
                        shape = MaterialShapes.Cookie9Sided.toShape(),
                        progressIndicatorSize = 56.dp
                    )
                    Text(
                        data.name,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Gap(8.dp)
                    MediaChip(
                        data.knownForDepartment,
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        icon = R.drawable.work_24px
                    )
                    Gap(12.dp)
                    Row() {
                        PersonDetailChip(data.birthday, R.drawable.cake_24px)
                        Gap(horizontal = 5.dp)
                        PersonDetailChip(
                            if (data.gender == 1) "Female" else "Male",
                            if (data.gender == 1) R.drawable.female_24px else R.drawable.male_24px
                        )
                    }

                    Gap(16.dp)

                    MediaSectionCard(
                        title = "Biography",
                        titleIcon = R.drawable.article_person_24px
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = data.biography,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }

                item {
                    Gap(WindowInsets.systemBars.asPaddingValues().calculateBottomPadding() + 30.dp)
                }
            }
        }
}

@Composable
private fun PersonDetailChip(text: String, icon: Int) {

    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(ShapeRadius.Full)
    ) {
        Column(
            modifier = Modifier.size(100.dp, 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Symbol(
                icon,
                size = 18.dp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Gap(3.dp)
            Text(
                text,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}
