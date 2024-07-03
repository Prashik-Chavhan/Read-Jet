package com.pc.readjet.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pc.readjet.R
import com.pc.readjet.data.model.Article

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BreakingNews(
    headlines: List<Article>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        pageCount = {headlines.size}
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = "Breaking News",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp
        ) {page ->
            Card(
                modifier = modifier
                    .height(235.dp),
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(headlines[page].urlToImage)
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        placeholder = painterResource(id = R.drawable.ic_loading),
                        error = painterResource(id = R.drawable.ic_broken)
                    )
                    Text(
                        text = headlines[page].title,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp),
                        maxLines = 2,
                        fontSize = 20.sp,
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        PageIndicator(
            pageCount = headlines.size,
            pagerState = pagerState.currentPage,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun PageIndicator(
    pageCount: Int,
    pagerState: Int,
    modifier: Modifier
) {
    Row(
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(
                isSelected = it == pagerState,
                modifier = modifier
            )
        }
    }
}

@Composable
fun IndicatorDots(
    isSelected: Boolean,
    modifier: Modifier
) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(modifier = modifier
        .padding(2.dp)
        .size(size.value)
        .clip(CircleShape)
        .background(
            if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                Color.LightGray
            }
        )
    )
}