package com.pc.readjet.presentation.search_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.pc.readjet.data.model.Article
import com.pc.readjet.presentation.components.NewsArticleCard
import com.pc.readjet.presentation.components.Retry
import com.pc.readjet.presentation.main.MainEvents
import com.pc.readjet.presentation.main.MainUiState

@Composable
fun SearchScreen(
    onEvent: (MainEvents) -> Unit,
    state: MainUiState,
    onNewsCardClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        SearchBar(
            modifier = Modifier.focusRequester(focusRequester),
            value = state.searchQuery,
            onValueChanged = {newValue ->
                             onEvent(MainEvents.onSearchQueryChanged(newValue)
                             )
            },
            onSearchClicked = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchArticleList(
            state = state,
            onRetry = {
                      onEvent(MainEvents.onSearchQueryChanged(state.searchQuery))
            },
            onCardClicked = {
                onNewsCardClicked(it.url)
            }
        )
    }
}

@Composable
fun SearchArticleList(
    state: MainUiState,
    onRetry: () -> Unit,
    onCardClicked: (Article) -> Unit
) {
    LazyColumn {
        items(state.articles) {article ->
            NewsArticleCard(
                article = article,
                onCardClicked = onCardClicked,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
    if(state.isLoading) {
        ShimmerList()
    }
}

@Composable
fun SearchBar(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChanged,
        placeholder = {Text("Search")},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.LightGray
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {onSearchClicked()}
        ),
        singleLine = true,
        shape = RoundedCornerShape(8.dp)
    )
}