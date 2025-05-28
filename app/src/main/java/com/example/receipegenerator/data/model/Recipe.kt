package com.example.receipegenerator.data.model

import java.io.Serializable

data class Recipe(
    val title: String,
    val preparation: String,
    val duration: String,
    val ingredients: List<String>
) : Serializable