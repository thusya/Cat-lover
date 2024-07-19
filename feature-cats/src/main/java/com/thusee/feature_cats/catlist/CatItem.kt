package com.thusee.feature_cats.catlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.thusee.core_data.model.Cat
import com.thusee.core_theme.spacing
import com.thusee.feature_cats.R

@Composable
fun CatItem(
    cat: Cat,
    onFavorite: () -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false
) {
    Card(
        modifier = modifier
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small,
            )
            .fillMaxWidth()
            .height(MaterialTheme.spacing.spacing100)
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onItemClick)
                .padding(MaterialTheme.spacing.small)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(cat.imageUrl)
                        .apply {
                            placeholder(R.drawable.android)
                            crossfade(true)
                        }
                        .build()
                ),
                contentDescription = stringResource(id = R.string.cat_image),
                modifier = Modifier
                    .size(MaterialTheme.spacing.spacing80)
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cat.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                Text(
                    text = cat.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))

            }

            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onFavorite) {
                    Icon(
                        imageVector = if (isFavorite) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Filled.FavoriteBorder
                        },
                        contentDescription = stringResource(id = R.string.favorite)
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun CatItemPreview() {
    val cat = Cat(
        id = "1",
        name = "Persian",
        imageUrl = "https://test.com/image",
        description = "The Persian cat is a long-haired breed of cat characterized by its round face and short muzzle.",
        isFavorite = false
    )
    MaterialTheme {
        CatItem(cat = cat, onItemClick = {}, onFavorite = {})
    }
}