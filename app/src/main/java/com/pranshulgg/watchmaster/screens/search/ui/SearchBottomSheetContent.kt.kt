package com.pranshulgg.watchmaster.screens.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.model.SearchType
import com.pranshulgg.watchmaster.screens.search.SearchViewModel
import com.pranshulgg.watchmaster.screens.search.SearchViewModelFactory
import com.pranshulgg.watchmaster.utils.Symbol

@Composable
fun SearchBottomSheetContent(
    viewModel: SearchViewModel = viewModel(factory = SearchViewModelFactory(LocalContext.current)),
    query: String,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    type: SearchType
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 14.dp, start = 12.dp, end = 12.dp, bottom = 10.dp)
            .imePadding()

    ) {

        TextField(
            value = query,
            onValueChange = viewModel::onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            placeholder = { Text("Search…") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.search(type)
                    focusManager.clearFocus()
                }
            ),
            leadingIcon = {
                Box(Modifier.padding(start = 6.dp)) {
                    IconButton(

                        onClick = {
                            focusRequester.requestFocus()
                        },
                    ) {
                        Symbol(
                            R.drawable.search_24px,
                        )
                    }
                }
            },
            trailingIcon = {

                if (query.isNotEmpty()) {
                    Box(Modifier.padding(end = 6.dp)) {

                        IconButton(
                            onClick = {
                                viewModel.onQueryChange("")
                                focusManager.clearFocus()
                            }
                        ) {
                            Symbol(
                                R.drawable.close_24px,
                            )
                        }
                    }
                }
            },
            shape = CircleShape,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

    }
}
