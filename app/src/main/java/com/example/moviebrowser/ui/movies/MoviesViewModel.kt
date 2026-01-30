package com.example.moviebrowser.ui.movies

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
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

class MoviesViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val moviesRepository = MoviesRepository(RetrofitClient.api)

    private val favoritesRepository = FavoritesRepository(
        DatabaseProvider
            .getDatabase(application)
            .favoritesDao()
    )

    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    val uiState: StateFlow<MoviesUiState> = _uiState

    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds

    private var currentPage = 1
    private var isLoading = false
    private val allMovies = mutableListOf<MovieDto>()

    init {
        observeFavorites()
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoading) return

        viewModelScope.launch {
            isLoading = true

            try {
                val newMovies = moviesRepository.getMovies(currentPage)
                allMovies.addAll(newMovies)
                currentPage++

                _uiState.value = MoviesUiState.Success(
                    movies = allMovies.toList(),
                    isLoadingMore = false
                )
            } catch (e: Exception) {
                _uiState.value = MoviesUiState.Error
                Log.d("DEBUG", e.toString())
            }

            isLoading = false
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            favoritesRepository.getFavorites()
                .collect { favorites ->
                    _favoriteIds.value = favorites.map { it.id }.toSet()
                }
        }
    }


    fun toggleFavorite(movie: MovieDto) {
        viewModelScope.launch {
            if (_favoriteIds.value.contains(movie.id)) {
                favoritesRepository.removeFromFavorites(movie.id)
            } else {
                favoritesRepository.addToFavorites(
                    FavoriteMovieEntity(
                        id = movie.id,
                        title = movie.title,
                        overview = movie.overview,
                        posterPath = movie.posterPath,
                        releaseDate = movie.releaseDate
                    )
                )
            }
        }
    }
}
