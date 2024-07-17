package com.thusee.feature_cats.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.thusee.core_theme.spacing
import com.thusee.feature_cats.CatViewModel
import com.thusee.feature_cats.R

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: CatViewModel = hiltViewModel()
) {
    CharacterList(
        list = listOf<String>(),
        onItemClick = {},
        modifier = modifier
    )
}

@Composable
fun CharacterList(
    list: List<String>,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
    ) {
        items(list) {
            ListItem(onItemClick = { onItemClick() })
        }
    }
}

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small,
            )
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onItemClick)
                .padding(MaterialTheme.spacing.small)
        ) {
            Image(
                painter = painterResource(id = R.drawable.android),
                contentDescription = "Image",
                modifier = Modifier
                    .size(64.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Name",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                Text(
                    text = "description",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(painterResource(id = R.drawable.baseline_favorite), contentDescription = "")
            }
        }
    }
}

@Preview
@Composable
fun ListItemPreview() {
    ListItem(onItemClick = {})
}