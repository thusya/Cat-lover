package com.thusee.catlover.navigation


internal const val CAT_ID = "CAT_ID"

sealed class NavigationScreen(val route: String) {
    data object CatList : NavigationScreen(route = "catList")
    data object CatDetails : NavigationScreen(route = "catDetails/{$CAT_ID}")
}
