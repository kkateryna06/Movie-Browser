package com.example.moviebrowser.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.moviebrowser.data.api.RetrofitClient
import com.example.moviebrowser.data.model.MovieDto
import com.example.moviebrowser.data.repository.FavoritesRepository
import com.example.moviebrowser.data.repository.MoviesRepository
import com.example.moviebrowser.db.DatabaseProvider
import com.example.moviebrowser.db.FavoriteMovieEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val repository = MoviesRepository(RetrofitClient.api)

    private val favoritesRepository = FavoritesRepository(
        DatabaseProvider
            .getDatabase(application)
            .favoritesDao()
    )

    private val movieId: Int = savedStateHandle["movieId"]!!

    private val _movie = MutableStateFlow<MovieDto?>(null)
    val movie: StateFlow<MovieDto?> = _movie

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    init {
        loadMovie()
    }

    private fun loadMovie() {
        viewModelScope.launch {
            val movie = repository.getMovieById(movieId)
            _movie.value = movie
            _isFavorite.value = favoritesRepository.isFavorite(movieId)
        }
    }


    fun toggleFavorite() {
        viewModelScope.launch {
            val current = movie.value ?: return@launch

            if (_isFavorite.value) {
                favoritesRepository.removeFromFavorites(current.id)
            } else {
                favoritesRepository.addToFavorites(
                    FavoriteMovieEntity(
                        id = current.id,
                        title = current.title,
                        overview = current.overview,
                        posterPath = current.posterPath,
                        releaseDate = current.releaseDate
                    )
                )
            }
            _isFavorite.value = !_isFavorite.value
        }
    }
}
