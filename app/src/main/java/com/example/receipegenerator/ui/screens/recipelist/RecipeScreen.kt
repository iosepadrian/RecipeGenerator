package com.example.receipegenerator.ui.screens.recipelist

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.receipegenerator.R
import com.example.receipegenerator.components.DislikeButton
import com.example.receipegenerator.components.LoadingComponent
import com.example.receipegenerator.components.RecipeCard
import com.example.receipegenerator.components.RecipeSearchBar
import com.example.receipegenerator.ui.navigation.Screen
import com.google.gson.Gson

@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel(),
    navController: NavController
) {
    val recipes by viewModel.recipes.collectAsState()
    val favorites by viewModel.favoriteTitles.collectAsState()
    val loading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var query by remember { mutableStateOf("") }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        RecipeSearchBar(
            query = query,
            onQueryChange = {
                query = it
            },
            onSearch = {
                keyboardController?.hide()
                viewModel.fetchRecipes(context.getString(R.string.base_prompt, query))
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate(Screen.Favorites.route) },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(context.getString(R.string.view_favorites))
            }
        }

        if (loading) {
            LoadingComponent()
        } else {
            Text(
                text = context.getString(R.string.suggested_recipe),
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp),
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(recipes) { recipe ->
                    val isFavorite = favorites.contains(recipe.title)
                    RecipeCard(recipe = recipe, isAddedToFav = isFavorite, clickFavButton = {
                        viewModel.toggleFavorite(recipe)
                    }, onClick = {
                        val recipeJson = Gson().toJson(recipe)
                        navController.navigate(Screen.RecipeDetail.createRouteWithArgs(recipeJson))
                    })
                }
            }
            DislikeButton(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                viewModel.fetchRecipes(context.getString(R.string.base_prompt, query))
            }
        }
        if (errorMessage != null) {

            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            LaunchedEffect(errorMessage) {
                viewModel.clearError()
            }
        }
    }

}