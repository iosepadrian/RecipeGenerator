package com.example.receipegenerator.network

import android.util.Log
import com.example.receipegenerator.data.model.Recipe
import com.example.receipegenerator.util.extractJsonArray
import com.example.receipegenerator.util.Constants
import com.google.ai.client.generativeai.GenerativeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class GeminiApi @Inject constructor(private val gson: Gson) {

    private val generativeModel = GenerativeModel(
        modelName = Constants.AI_MODEL,
        apiKey = Constants.API_KEY,
        generationConfig = Constants.CONFIG
    )

    suspend fun getRecipes(prompt: String): List<Recipe> {
        return try {
            val rawResponse = generativeModel.generateContent(prompt).text ?: ""
            Log.d("Gemini", "Raw response: $rawResponse")

            val jsonOnly = rawResponse.extractJsonArray()
            if (jsonOnly != null) {
                val type = object : TypeToken<List<Recipe>>() {}.type
                gson.fromJson(jsonOnly, type)
            } else {
                Log.e("Gemini", "No valid JSON array found in response.")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("Gemini", "Error parsing recipes: ${e.message}")
            throw e
        }
    }
}