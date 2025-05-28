package com.example.receipegenerator.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.receipegenerator.data.local.dao.RecipeDao
import com.example.receipegenerator.data.local.entity.FavoriteRecipeEntity

@Database(entities = [FavoriteRecipeEntity::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}