package com.example.receipegenerator.data.repository

import com.example.receipegenerator.data.model.Recipe
import com.example.receipegenerator.network.GeminiApi
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val api: GeminiApi
) {
    suspend fun fetchRecipes(prompt: String): List<Recipe> {
        return api.getRecipes(prompt)
    }
}