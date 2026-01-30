package com.example.moviebrowser.data.repository

import com.example.moviebrowser.db.FavoriteMovieEntity
import com.example.moviebrowser.db.FavoritesDao
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(
    private val dao: FavoritesDao
) {

    fun getFavorites(): Flow<List<FavoriteMovieEntity>> =
        dao.getAllFavorites()

    suspend fun addToFavorites(movie: FavoriteMovieEntity) {
        dao.insert(movie)
    }

    suspend fun removeFromFavorites(movieId: Int) {
        dao.delete(movieId)
    }

    suspend fun isFavorite(movieId: Int): Boolean {
        return dao.isFavorite(movieId)
    }
}
