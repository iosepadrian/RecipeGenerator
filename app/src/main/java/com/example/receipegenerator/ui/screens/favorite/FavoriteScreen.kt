package com.example.receipegenerator.ui.screens.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.receipegenerator.components.RecipeCard
import com.google.gson.Gson
import com.example.receipegenerator.R
import com.example.receipegenerator.ui.navigation.Screen

@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val favorites by viewModel.favorites.collectAsState()
    viewModel.loadFavorites()
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = context.getString(R.string.back)
                )
            }
            Text(
                text = context.getString(R.string.you_favorite_recipes),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(favorites) { recipe ->
                RecipeCard(
                    recipe = recipe,
                    isAddedToFav = true,
                    clickFavButton = { viewModel.removeFromFavorites(recipe) },
                    onClick = {
                        val recipeJson = Gson().toJson(recipe)
                        navController.navigate(Screen.RecipeDetail.createRouteWithArgs(recipeJson))
                    }
                )
            }
        }
    }
}