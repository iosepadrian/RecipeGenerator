package com.example.receipegenerator.ui.screens.recipedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receipegenerator.data.model.Recipe
import com.example.receipegenerator.data.repository.FavRecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val repository: FavRecipeRepository
) : ViewModel() {

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun checkIfFavorite(recipe: Recipe) {
        viewModelScope.launch {
            _isFavorite.value = repository.isFavorite(recipe)
        }
    }

    fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch {
            val currentlyFav = repository.isFavorite(recipe)
            if (currentlyFav) {
                repository.removeFavorite(recipe)
                _isFavorite.value = false
            } else {
                repository.addFavorite(recipe)
                _isFavorite.value = true
            }
        }
    }
}
