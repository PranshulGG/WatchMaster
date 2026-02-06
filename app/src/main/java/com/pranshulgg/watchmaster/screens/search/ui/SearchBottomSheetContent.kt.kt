package com.pranshulgg.watchmaster.screens.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
//    Box(
//        Modifier
//            .width(220.dp)
//    ) {

    TextField(
        value = query,
        onValueChange = viewModel::onQueryChange,

        modifier = Modifier
            .width(220.dp)
//            .height(50.dp)
//            .padding(0.dp)
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
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        ),
    )

//    }
}
