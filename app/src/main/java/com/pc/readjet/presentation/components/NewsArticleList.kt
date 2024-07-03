package com.pc.readjet.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pc.readjet.R
import com.pc.readjet.data.model.Article
import com.pc.readjet.presentation.main.MainUiState

@Composable
fun NewsArticleList(
    state: MainUiState,
    onRetry: () -> Unit,
    onCardClicked: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(state.articles) {article ->
            NewsArticleCard(
                article = article,
                onCardClicked = onCardClicked,
                modifier = modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        if (state.error != null) {
            Retry(onRetry = { onRetry() })
        }
    }
}

@Composable
fun Retry(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = "")
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = { onRetry() }) {
            Text(text = "Retry")
        }
    }
}