package com.pc.readjet.presentation.home_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pc.readjet.presentation.components.BreakingNews
import com.pc.readjet.presentation.components.CategoryTabRow
import com.pc.readjet.presentation.components.NewsArticleList
import com.pc.readjet.presentation.main.MainEvents
import com.pc.readjet.presentation.main.MainUiState
import com.pc.readjet.presentation.main.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    state: MainUiState,
    onEvent: (MainEvents) -> Unit,
    onNewsCardClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: MainViewModel = hiltViewModel()
    val breakingNews = viewModel.breakingNews

    val pagerState = rememberPagerState(pageCount = {6})
    val coroutineScope = rememberCoroutineScope()

    val categories = listOf(
        "Business", "Entertainment", "Health", "Science", "Sports", "Technology")

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {page ->
            onEvent(MainEvents.onCategoryChanged(category = categories[page]))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        BreakingNews(
            headlines = breakingNews
        )
        CategoryTabRow(
            pagerState = pagerState,
            categories = categories,
            onTabSelect = { index ->
                coroutineScope.launch { pagerState.animateScrollToPage(index) }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalPager(state = pagerState) {
            NewsArticleList(
                state = state,
                onRetry = {
                          onEvent(MainEvents.onCategoryChanged(state.category))
                },
                onCardClicked = {
                    onNewsCardClicked(it.url)
                }
            )
        }
    }
}