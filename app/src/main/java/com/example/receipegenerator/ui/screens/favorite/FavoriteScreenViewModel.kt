package com.example.receipegenerator.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receipegenerator.data.model.Recipe
import com.example.receipegenerator.data.repository.FavRecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavRecipeRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Recipe>>(emptyList())
    val favorites: StateFlow<List<Recipe>> = _favorites.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getFavorites()
        }
    }

    fun removeFromFavorites(recipe: Recipe) {
        viewModelScope.launch {
            repository.removeFavorite(recipe)
            loadFavorites()
        }
    }
}