package com.example.receipegenerator.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.receipegenerator.data.local.entity.FavoriteRecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    suspend fun getAll(): List<FavoriteRecipeEntity>

    @Query("SELECT * FROM recipes")
    fun getAllFlow(): Flow<List<FavoriteRecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: FavoriteRecipeEntity)

    @Delete
    suspend fun delete(recipe: FavoriteRecipeEntity)

    @Query("SELECT EXISTS(SELECT * FROM recipes WHERE title = :title)")
    suspend fun isFavorite(title: String): Boolean
}