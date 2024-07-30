package com.thusee.feature_cats.catdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.thusee.core_theme.spacing
import com.thusee.feature_cats.R
import com.thusee.feature_cats.components.CustomToolbar

@Composable
fun CatDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: CatDetailsViewModel = hiltViewModel()
) {
    val cat by viewModel.catDetails.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CustomToolbar(
                title = cat.name,
                showBackButton = true,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    ) { paddingValues ->
        val state = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(state)
                .padding(paddingValues)
        ) {
            Image(
                painter = rememberAsyncImagePainter(cat.imageUrl),
                contentDescription = stringResource(id = R.string.cat_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .padding(MaterialTheme.spacing.medium),
                contentScale = ContentScale.FillHeight
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.small,
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.medium)
                ) {
                    Text(
                        text = stringResource(R.string.text_origin, cat.origin),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(R.string.text_life_span, cat.lifeSpan),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Text(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.medium),
                text = stringResource(R.string.text_description),
                style = MaterialTheme.typography.titleMedium
            )
            Card(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.spacing.medium,
                        vertical = MaterialTheme.spacing.small
                    )
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.medium),
                    text = cat.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}