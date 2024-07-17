package com.thusee.core_data.async

sealed class CatRepoAsyncEvents {
    data object LoadCatsSuccess : CatRepoAsyncEvents()
    data object LoadCatsFailed : CatRepoAsyncEvents()
    data object FavoriteCatsSuccess: CatRepoAsyncEvents()
    data object FavoriteCatsFailed: CatRepoAsyncEvents()
    data object UnFavoriteCatsSuccess : CatRepoAsyncEvents()
    data object UnFavoriteCatsFailed: CatRepoAsyncEvents()
}