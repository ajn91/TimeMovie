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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jafari.movie.R
import jafari.movie.presentation.feature.movielist.component.MovieList
import jafari.movie.presentation.ui.UiText

@Composable
fun MovieListScreen(
  movieListUiState: MovieListUiState,
  errorMessage: UiText?,
  clearErrorMessage: () -> Unit,
  refreshClicked: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val snackbarHostState = remember { SnackbarHostState() }

  val context = LocalContext.current
  LaunchedEffect(errorMessage) {
    if (errorMessage != null) {
      val errorString = errorMessage.asString(context)
      snackbarHostState.showSnackbar(message = errorString)
      clearErrorMessage()
    }
  }
  LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
    clearErrorMessage()
  }

  Scaffold(
    snackbarHost = {
      SnackbarHost(hostState = snackbarHostState)
    },
    modifier = modifier.fillMaxSize(),
  ) { contentPadding ->
    Box(modifier = Modifier.padding(contentPadding)) {
      when (movieListUiState) {
        is MovieListUiState.LoadFailed -> {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center),
          ) {

            Text(
              text = movieListUiState.uiText.asString(),
              textAlign = TextAlign.Center,
              modifier = Modifier.padding(bottom = 16.dp),
            )
            Button(onClick = { refreshClicked() }) {
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
}

@Composable
fun MovieListScreen(
  viewModel: MovieListViewModel = hiltViewModel(),
  modifier: Modifier = Modifier,
) {
  val movieListUiState by viewModel.movieListState.collectAsStateWithLifecycle()
  MovieListScreen(movieListUiState,
    viewModel.errorMessage,
    clearErrorMessage ={viewModel.onEvent(MovieListEvent.ClearErrorMessage)} ,
    refreshClicked = {viewModel.onEvent(MovieListEvent.RefreshClicked)},
    modifier)
}
