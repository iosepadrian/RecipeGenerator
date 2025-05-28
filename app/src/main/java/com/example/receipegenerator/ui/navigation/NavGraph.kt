package com.example.receipegenerator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.receipegenerator.data.model.Recipe
import com.example.receipegenerator.ui.screens.favorite.FavoriteScreen
import com.example.receipegenerator.ui.screens.favorite.FavoriteViewModel
import com.example.receipegenerator.ui.screens.recipedetails.RecipeDetailScreen
import com.example.receipegenerator.ui.screens.recipelist.RecipeScreen
import com.google.gson.Gson

@Composable
fun NavGraph(navController: androidx.navigation.NavHostController) {
    NavHost(navController = navController, startDestination = Screen.RecipeList.route) {
        composable(Screen.RecipeList.route) {
            RecipeScreen(navController = navController)
        }

        composable(
            route = Screen.RecipeDetail.route,
            arguments = listOf(navArgument(Screen.RecipeDetail.ARG_RECIPE) { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString(Screen.RecipeDetail.ARG_RECIPE)
            val recipe = Gson().fromJson(json, Recipe::class.java)
            RecipeDetailScreen(recipe, navController)
        }

        composable(Screen.Favorites.route) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.RecipeList.route)
            }
            val viewModel: FavoriteViewModel = hiltViewModel(parentEntry)
            FavoriteScreen(navController = navController, viewModel = viewModel)
        }
    }
}

sealed class Screen(val route: String) {
    object RecipeList : Screen("recipe_list")
    object Favorites : Screen("favorites")
    object RecipeDetail : Screen("recipe_detail/{recipe}") {
        const val ARG_RECIPE = "recipe"
        fun createRouteWithArgs(recipeJson: String) = "recipe_detail/$recipeJson"
    }
}