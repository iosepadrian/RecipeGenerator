package com.example.receipegenerator.ui.screens.recipedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.receipegenerator.R
import com.example.receipegenerator.data.model.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    navController: NavController,
    viewModel: RecipeDetailsViewModel = hiltViewModel()
) {
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(recipe.title) {
        viewModel.checkIfFavorite(recipe)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.height(250.dp)) {
            Image(
                painter = painterResource(id = R.drawable.placeholder), // Add placeholder.png to res/drawable
                contentDescription = context.getString(R.string.recipe_image_content_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                alpha = 0.3f
            )

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = context.getString(R.string.back))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(0.75f)) {
                    Text(text = recipe.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(
                        text = context.getString(R.string.duration, recipe.duration),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                IconButton(
                    onClick = { viewModel.toggleFavorite(recipe) },
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .align(Alignment.CenterVertically)
                        .weight(0.25f),

                    ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = context.getString(R.string.favorite),
                        tint = if (isFavorite) Color(0xFF6A4D9A) else Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = context.getString(R.string.ingredients), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            recipe.ingredients.forEach {
                Text("• $it", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = context.getString(R.string.instructions), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            recipe.preparation.split(".").map { it.trim() }.filter { !it.isEmpty() }
                .forEachIndexed { index, step ->
                    Text(text = "${index + 1}. $step", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(8.dp))
                }
        }
    }
}