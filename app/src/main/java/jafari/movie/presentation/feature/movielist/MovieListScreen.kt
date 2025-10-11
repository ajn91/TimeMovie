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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jafari.movie.R
import jafari.movie.domain.models.Movie
import jafari.movie.presentation.feature.movielist.component.MovieList
import jafari.movie.presentation.ui.UiText
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel = hiltViewModel(),
) {
    val movieListUiState by viewModel.movieListState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is MovieListEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }

    MovieListScreenContent(
        movieListUiState = movieListUiState,
        snackBarHostState = snackBarHostState,
        onRefreshClicked = { viewModel.onEvent(MovieListAction.RefreshClicked) },
        modifier = modifier,
    )
}

@Composable
internal fun MovieListScreenContent(
    movieListUiState: MovieListUiState,
    snackBarHostState: SnackbarHostState,
    onRefreshClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
                    ErrorContainer(
                        errorText = movieListUiState.error,
                        onRefreshClicked = onRefreshClicked
                    )
                }

                is MovieListUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.tertiary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

                is MovieListUiState.Success -> {
                    Box(Modifier.fillMaxSize()) {
                        MovieList(
                            list = movieListUiState.movieList,
                            onItemClicked = {},
                            modifier = Modifier.fillMaxSize(),
                        )
                        if (movieListUiState.isRefreshing) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 16.dp),
                            )
                        }
                    }
                }

                is MovieListUiState.Empty -> {
                    Text(text = stringResource(R.string.not_found))
                }
            }
        }
    }
}

@Composable
private fun ErrorContainer(
    errorText: UiText,
    onRefreshClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
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


// Previews
private fun getDummyMovies(): List<Movie> {
    return List(10) { index ->
        Movie(
            id = index,
            overview = "This is a overview for movie$index",
            posterUrl = "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg",
            releaseDate = "$index/$index/$index",
            title = "Title $index",
        )
    }
}

@Preview(name = "Loading State")
@Composable
private fun MovieListScreenLoadingPreview() {
    MovieListScreenContent(
        movieListUiState = MovieListUiState.Loading,
        snackBarHostState = remember { SnackbarHostState() },
        onRefreshClicked = {},
        modifier = Modifier.fillMaxSize(),
    )
}

@Preview(name = "Empty State")
@Composable
private fun MovieListScreenEmptyPreview() {
    MovieListScreenContent(
        movieListUiState = MovieListUiState.Empty,
        snackBarHostState = remember { SnackbarHostState() },
        onRefreshClicked = {},
        modifier = Modifier.fillMaxSize(),
    )
}

@Preview(name = "Success State")
@Composable
private fun MovieListScreenSuccessPreview() {
    MovieListScreenContent(
        movieListUiState = MovieListUiState.Success(
            movieList = getDummyMovies(),
            isRefreshing = false,
        ),
        snackBarHostState = remember { SnackbarHostState() },
        onRefreshClicked = {},
        modifier = Modifier.fillMaxSize(),
    )
}

@Preview(name = "Success State (Refreshing)")
@Composable
private fun MovieListScreenRefreshingPreview() {
    MovieListScreenContent(
        movieListUiState = MovieListUiState.Success(
            movieList = getDummyMovies(),
            isRefreshing = true,
        ),
        snackBarHostState = remember { SnackbarHostState() },
        onRefreshClicked = {},
        modifier = Modifier.fillMaxSize(),
    )
}

@Preview(name = "Error State")
@Composable
private fun MovieListScreenErrorPreview() {
    MovieListScreenContent(
        movieListUiState = MovieListUiState.LoadFailed(UiText.DynamicString("Could not load movies")),
        snackBarHostState = remember { SnackbarHostState() },
        onRefreshClicked = {},
        modifier = Modifier.fillMaxSize(),
    )
}
