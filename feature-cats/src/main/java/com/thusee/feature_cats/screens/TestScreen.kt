package com.thusee.feature_cats.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thusee.feature_cats.CatViewModel

@Composable
fun TestScreen(
    modifier: Modifier = Modifier,
    viewModel: CatViewModel = hiltViewModel(),
) {
    val catsUIState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchCats()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(catsUIState.items) {
            Log.d("Thusee data : ", catsUIState.items.toString())
            CatItem(
                breed = it.breed,
                isFavorite = it.isFavorite
            ) {
                viewModel.onFavorite(it)
            }
        }
    }
}