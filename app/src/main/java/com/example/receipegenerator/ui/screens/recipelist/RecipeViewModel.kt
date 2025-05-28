package com.example.receipegenerator.ui.screens.recipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receipegenerator.data.model.Recipe
import com.example.receipegenerator.data.repository.FavRecipeRepository
import com.example.receipegenerator.data.repository.RecipeRepository
import com.example.receipegenerator.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val favRecipeRepository: FavRecipeRepository
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _favoriteTitles = MutableStateFlow<Set<String>>(emptySet())
    val favoriteTitles: StateFlow<Set<String>> = _favoriteTitles

    init {
        observeFavorites()
        fetchRecipes(Constants.INITIAL_PROMPT)
    }

    fun fetchRecipes(prompt: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.fetchRecipes(prompt)
                _recipes.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            favRecipeRepository.favoritesFlow.collect { favorites ->
                _favoriteTitles.value = favorites.map { it.title }.toSet()
            }
        }
    }

    fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch {
            if (_favoriteTitles.value.contains(recipe.title)) {
                favRecipeRepository.removeFavorite(recipe)
            } else {
                favRecipeRepository.addFavorite(recipe)
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}