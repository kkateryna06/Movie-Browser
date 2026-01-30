package com.example.moviebrowser.data.repository

import com.example.moviebrowser.BuildConfig
import com.example.moviebrowser.data.api.TmdbApi
import com.example.moviebrowser.data.model.MovieDto

class MoviesRepository(private val api: TmdbApi) {
    suspend fun getMovies(page: Int): List<MovieDto> {
        return api.getPopularMovies(
            apiKey = BuildConfig.API_KEY,
            page = page
        ).results
    }

    suspend fun getMovieById(id: Int): MovieDto {
        return api.getMovieDetails(
            movieId = id,
            apiKey = BuildConfig.API_KEY
        )
    }

}