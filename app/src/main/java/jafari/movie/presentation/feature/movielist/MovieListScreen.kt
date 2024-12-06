package jafari.movie.presentation.feature.movielist

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jafari.movie.R
import jafari.movie.domain.models.Movie
import jafari.movie.presentation.feature.movielist.component.MovieList
import jafari.movie.presentation.ui.UiText
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MovieListScreen(
  movieListUiState: MovieListUiState,
  uiEvent: SharedFlow<UiEvent>,
  refreshClicked: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val snackbarHostState = remember { SnackbarHostState() }

  val context = LocalContext.current
  LaunchedEffect(key1 = uiEvent) {
    uiEvent.collectLatest { event ->
      when (event) {
        is UiEvent.ShowErrorMessage -> {
          val errorString = event.error.asString(context)
          snackbarHostState.showSnackbar(message = errorString)
        }
      }
    }
  }
  LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
    snackbarHostState.currentSnackbarData?.dismiss()
  }

  Scaffold(
    snackbarHost = {
      SnackbarHost(hostState = snackbarHostState)
    },
    modifier = modifier,
  ) { contentPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(contentPadding),
    ) {
      when (movieListUiState) {
        is MovieListUiState.LoadFailed -> {
          ErrorContainer(movieListUiState.uiText, refreshClicked)
        }

        is MovieListUiState.Loading -> {
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
          MovieList(list = movieListUiState.movieList, onItemClicked = {}, Modifier.fillMaxSize())
        }

      }
    }

  }
}

@Composable
fun ErrorContainer(errorText: UiText, refreshClicked: () -> Unit) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {

    Text(
      text = errorText.asString(),
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(bottom = 16.dp),
    )
    Button(onClick = { refreshClicked() }) {
      Text(text = stringResource(R.string.refresh))
    }
  }
}

@Composable
fun MovieListScreen(
  viewModel: MovieListViewModel = hiltViewModel(),
  modifier: Modifier = Modifier,
) {
  val movieListUiState by viewModel.movieListState.collectAsStateWithLifecycle()
  MovieListScreen(
    movieListUiState,
    viewModel.uiEventFlow,
    refreshClicked = { viewModel.onEvent(MovieListEvent.RefreshClicked) },
    modifier,
  )
}

@Preview
@Composable
fun MovieListScreen() {
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
    MovieListUiState.Success(movies),
    MutableSharedFlow<UiEvent>(),
    refreshClicked = { },
    Modifier.fillMaxSize(),
  )
}
