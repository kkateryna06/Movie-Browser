package com.example.moviebrowser

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviebrowser.ui.components.MoviesTopBar
import com.example.moviebrowser.ui.details.MovieDetailsScreen
import com.example.moviebrowser.ui.favorites.FavoritesScreen
import com.example.moviebrowser.ui.movies.MoviesScreen
import com.example.moviebrowser.ui.movies.MoviesViewModel

object Screen {
        const val MOVIES = "movies"
        const val DETAILS = "details"
        const val FAVORITES = "favorites"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = currentBackStackEntry
        ?.destination
        ?.route

    Scaffold(
        topBar = {
            when {
                currentRoute == Screen.MOVIES -> {
                    MoviesTopBar(
                        title = "Movies",
                        onFavoritesClick = {
                            navController.navigate(Screen.FAVORITES)
                        }
                    )
                }

                currentRoute == Screen.FAVORITES -> {
                    MoviesTopBar(
                        title = "Favorites",
                        onFavoritesClick = {
                            navController.popBackStack()
                        }
                    )
                }

                currentRoute?.startsWith(Screen.DETAILS) == true -> {
                    TopAppBar(
                        title = { Text(text = "Details",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold) },
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.popBackStack()
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Screen.MOVIES,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Screen.MOVIES) {
                MoviesScreen(
                    onMovieClick = { movieId ->
                        navController.navigate("${Screen.DETAILS}/$movieId")
                    }
                )
            }

            composable(
                route = "${Screen.DETAILS}/{movieId}",
                arguments = listOf(
                    navArgument("movieId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                MovieDetailsScreen(backStackEntry)
            }

            composable(Screen.FAVORITES) {
                FavoritesScreen(
                    onMovieClick = { movieId ->
                        navController.navigate("${Screen.DETAILS}/$movieId")
                    }
                )
            }
        }
    }
}

