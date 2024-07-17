package com.thusee.feature_cats.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.thusee.feature_cats.R

@Composable
fun CatItem(
    breed: String,
    isFavorite: Boolean = false,
    onFavorite: () -> Unit
) {
    Column {
        Text(breed)
        IconButton(
            onClick = onFavorite
        ) {
            Icon(
                if (isFavorite) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Filled.FavoriteBorder
                },
                stringResource(id = R.string.favorite)
            )
        }
    }
}