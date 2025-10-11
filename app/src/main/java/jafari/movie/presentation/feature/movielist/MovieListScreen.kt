package jafari.movie.presentation.feature.movielist

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jafari.movie.R
import jafari.movie.domain.models.Movie
import jafari.movie.presentation.feature.movielist.component.MovieList
import jafari.movie.presentation.ui.UiText

@Composable
fun MovieListScreen(
  modifier: Modifier = Modifier,
  viewModel: MovieListViewModel = hiltViewModel(),
) {
  val movieListUiState by viewModel.movieListState.collectAsStateWithLifecycle()
  MovieListScreen(
    movieListUiState = movieListUiState,
    onRefreshClicked = { viewModel.onEvent(MovieListAction.RefreshClicked) },
    modifier = modifier,
  )
}

@Composable
internal fun MovieListScreen(
  movieListUiState: MovieListUiState,
  onRefreshClicked: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val snackBarHostState = remember { SnackbarHostState() }
  val context = LocalContext.current

  // Show snackBar for errors that occur during a background refresh
  val error = movieListUiState.error
  LaunchedEffect(error) {
    if (movieListUiState is MovieListUiState.Success && error != null) {
      val errorString = error.asString(context)
      snackBarHostState.showSnackbar(message = errorString)
    }
  }

  SideEffect {
    Log.d("LOG", "MovieListScreen: recomposing")
  }

  LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
    snackBarHostState.currentSnackbarData?.dismiss()
  }

  Scaffold(
    snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    modifier = modifier,
  ) { contentPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(contentPadding),
      contentAlignment = Alignment.Center
    ) {
      when (movieListUiState) {
        is MovieListUiState.LoadFailed -> {
          ErrorContainer(errorText = movieListUiState.error, onRefreshClicked = onRefreshClicked)
        }

        is MovieListUiState.Loading -> {
          CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.tertiary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
          )
        }

        is MovieListUiState.Success -> {
          MovieList(list = movieListUiState.movieList, onItemClicked = {}, Modifier.fillMaxSize())
        }

        is MovieListUiState.Empty -> {
          Text(text = stringResource(R.string.not_found))
        }
      }
    }
  }
}

@Composable
private fun ErrorContainer(errorText: UiText, onRefreshClicked: () -> Unit) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = errorText.asString(),
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(bottom = 16.dp),
    )
    Button(onClick = onRefreshClicked) {
      Text(text = stringResource(R.string.refresh))
    }
  }
}

@Preview
@Composable
private fun MovieListScreenPreview() {
  val movies = List(10) { index ->
    Movie(
      id = index,
      overview = "This is a overview for movie$index",
      posterUrl = "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg",
      releaseDate = "$index/$index/$index",
      title = "Title $index",
    )
  }
  MovieListScreen(
    movieListUiState = MovieListUiState.Success(movies),
    onRefreshClicked = { },
    modifier = Modifier.fillMaxSize(),
  )
}
