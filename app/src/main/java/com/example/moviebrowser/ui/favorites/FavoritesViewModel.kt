package com.example.moviebrowser.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebrowser.data.repository.FavoritesRepository
import com.example.moviebrowser.db.DatabaseProvider
import com.example.moviebrowser.db.FavoriteMovieEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FavoritesRepository(
        DatabaseProvider
            .getDatabase(application)
            .favoritesDao()
    )

    val favorites: StateFlow<List<FavoriteMovieEntity>> =
        repository.getFavorites()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )
}
