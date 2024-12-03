package jafari.movie.presentation.feature.movielist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jafari.movie.R
import jafari.movie.presentation.feature.movielist.component.MovieList

@Composable
fun MovieListScreen(
  movieListUiState: MovieListUiState,
  refreshClicked: (MovieListEvent) -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(modifier = modifier.fillMaxSize()) {
    when (movieListUiState) {
      is MovieListUiState.LoadFailed -> {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.align(Alignment.Center)) {

          Text(
            text = movieListUiState.uiText.asString(),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp),
          )
          Button(onClick = { refreshClicked(MovieListEvent.RefreshClicked) }) {
            Text(text = stringResource(R.string.refresh))
          }
        }
      }

      MovieListUiState.Loading -> {
        CircularProgressIndicator(
          modifier =
          Modifier
            .width(64.dp)
            .align(Alignment.Center),
          color = MaterialTheme.colorScheme.tertiary,
          trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
      }

      is MovieListUiState.Success -> {
        MovieList(list = movieListUiState.movieList, onItemClicked = {})
      }
    }

  }
}

@Composable
fun MovieListScreen(
  viewModel: MovieListViewModel = hiltViewModel(),
  modifier: Modifier = Modifier,
) {
  val movieListUiState by viewModel.movieListState.collectAsStateWithLifecycle()
  MovieListScreen(movieListUiState, viewModel::onEvent, modifier)
}
