package com.thusee.feature_cats.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thusee.core_data.model.Cat
import com.thusee.feature_cats.CatViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatListScreen(
    modifier: Modifier = Modifier,
    viewModel: CatViewModel = hiltViewModel(),
) {
    val catsUIState by viewModel.uiState.collectAsStateWithLifecycle()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState.snackbarHostState)
        },
        content = { paddingValues ->
            Box(modifier = modifier.padding(paddingValues)) {
                when {
                    catsUIState.isLoading -> {}
                    else -> {
                        CatList(
                            list = catsUIState.items,
                            onItemClick = {},
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    )
    catsUIState.userMessage?.let { userMessage ->
        val message = stringResource(id = userMessage)

        LaunchedEffect(catsUIState, message) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = "",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}

@Composable
fun CatList(
    list: List<Cat>,
    onItemClick: () -> Unit,
    viewModel: CatViewModel,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(list) {
            CatItem(
                cat = it,
                isFavorite = it.isFavorite,
                onFavorite = { viewModel.onFavorite(cat = it) },
                onItemClick = { onItemClick() }
            )
        }
    }
}