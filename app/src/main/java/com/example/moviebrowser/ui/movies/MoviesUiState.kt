package com.example.moviebrowser.ui.movies

import com.example.moviebrowser.data.model.MovieDto

sealed interface MoviesUiState {
    object Loading : MoviesUiState
    data class Success(
        val movies: List<MovieDto>,
        val isLoadingMore: Boolean
    ) : MoviesUiState

    object Error : MoviesUiState
}
