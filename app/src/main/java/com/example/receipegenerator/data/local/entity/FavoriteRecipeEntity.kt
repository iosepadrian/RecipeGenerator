package com.example.receipegenerator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class FavoriteRecipeEntity(
    @PrimaryKey val title: String,
    val preparation: String,
    val duration: String,
    val ingredients: String // Stored as comma-separated
)