package com.thusee.feature_cats.catlist

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.thusee.core_data.model.Cat
import com.thusee.feature_cats.R
import com.thusee.feature_cats.components.CustomToolbar
import com.thusee.feature_cats.components.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatListScreen(
    modifier: Modifier = Modifier,
    viewModel: CatViewModel = hiltViewModel(),
    navController: NavController,
) {
    val catsUIState = viewModel.uiState.collectAsStateWithLifecycle()
    val scaffoldState = rememberBottomSheetScaffoldState()
    Scaffold(
        modifier = modifier,
        topBar = {
            CustomToolbar(
                title = stringResource(id = R.string.cat),
                onBack = {},
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState.snackbarHostState)
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                when (val state = catsUIState.value) {
                    is CatsUIState.Loading -> {
                        LoadingScreen()
                    }

                    is CatsUIState.Data -> {
                        CatList(
                            list = state.items,
                            onItemClick = { catId ->
                                navController.navigate(route = "catDetails/$catId")
                            },
                            viewModel = viewModel
                        )
                    }

                    is CatsUIState.Message -> {
                        val message = stringResource(id = state.messageId)
                        LaunchedEffect(scaffoldState.snackbarHostState) {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = message,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun CatList(
    list: List<Cat>,
    onItemClick: (String) -> Unit,
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
                onItemClick = { onItemClick(it.id) }
            )
        }
    }
}