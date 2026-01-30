package com.example.moviebrowser.ui.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage


@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel = viewModel(),
    onMovieClick: (Int) -> Unit,
) {
    val favorites by favoritesViewModel.favorites.collectAsState()

    LazyColumn {
        items(favorites) { movie ->
            Row(modifier = Modifier.padding(8.dp).clickable { onMovieClick(movie.id) }) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(movie.title, style = MaterialTheme.typography.titleMedium)
                    Text(movie.overview, maxLines = 3)
                }
            }
        }
    }
}
