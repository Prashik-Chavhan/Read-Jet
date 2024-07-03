package com.pc.readjet.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pc.readjet.R
import com.pc.readjet.data.model.Article

@Composable
fun NewsArticleCard(
    article: Article,
    onCardClicked: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onCardClicked(article) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .sizeIn(minHeight = 72.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(9.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(article.urlToImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_loading),
                    error = painterResource(id = R.drawable.ic_broken),
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Divider(
                modifier = Modifier
                    .height(80.dp)
                    .width(2.dp),
                color = Color.Red
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = article.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Text(
                    text = "--" .plus(article.source.name)
                )
            }
        }
    }
}