package com.example.receipegenerator.data.repository

import com.example.receipegenerator.data.local.dao.RecipeDao
import com.example.receipegenerator.data.local.entity.FavoriteRecipeEntity
import com.example.receipegenerator.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class FavRecipeRepository(private val dao: RecipeDao) {
    suspend fun getFavorites(): List<Recipe> =
        dao.getAll().map { it.toDomain() }

    suspend fun addFavorite(recipe: Recipe) {
        dao.insert(recipe.toEntity())
    }

    suspend fun removeFavorite(recipe: Recipe) {
        dao.delete(recipe.toEntity())
    }

    suspend fun isFavorite(recipe: Recipe): Boolean =
        dao.isFavorite(recipe.title)

    val favoritesFlow: Flow<List<Recipe>> =
        dao.getAllFlow().map { list -> list.map { it.toDomain() } }
}

fun Recipe.toEntity() = FavoriteRecipeEntity(
    title, preparation, duration, ingredients.joinToString(",")
)

fun FavoriteRecipeEntity.toDomain() = Recipe(
    title, preparation, duration, ingredients.split(",")
)