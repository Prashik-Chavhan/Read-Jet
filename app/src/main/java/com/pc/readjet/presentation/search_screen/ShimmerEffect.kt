package com.pc.readjet.presentation.search_screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerEffect() {
    val color = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    )
    val brush = Brush.linearGradient(
        colors = color,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    ShimmerItem(brush = brush)
}

@Composable
fun ShimmerList(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(10) {
            ShimmerEffect()
        }
    }
}

@Composable
fun ShimmerItem(
    brush: Brush,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .sizeIn(minHeight = 72.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(brush)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Divider(
            modifier = Modifier
                .height(80.dp)
                .width(2.dp)
                .background(brush)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .background(brush)
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
                    .background(brush)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ShimmerPreview(
    modifier: Modifier = Modifier
) {
    ShimmerList()
}