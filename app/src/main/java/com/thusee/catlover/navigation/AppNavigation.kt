package com.thusee.catlover.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thusee.feature_cats.catdetail.CatDetailsScreen
import com.thusee.feature_cats.catlist.CatListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "catList") {
        composable("catList") {
            CatListScreen(navController = navController)
        }
        composable("catDetails/{catId}") { backStackEntry ->
            val catId = backStackEntry.arguments?.getString("catId")
            CatDetailsScreen(navController = navController)
        }
    }
}