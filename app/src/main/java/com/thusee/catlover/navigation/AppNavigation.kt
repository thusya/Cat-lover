package com.thusee.catlover.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thusee.feature_cats.catdetail.CatDetailsScreen
import com.thusee.feature_cats.catlist.CatListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationScreen.CatList.route) {
        composable(route = NavigationScreen.CatList.route) {
            CatListScreen(navController = navController)
        }
        composable(
            route = NavigationScreen.CatDetails.route,
            arguments = listOf(navArgument(CAT_ID) { type = NavType.StringType })
        ) {
            CatDetailsScreen(navController = navController)
        }
    }
}

