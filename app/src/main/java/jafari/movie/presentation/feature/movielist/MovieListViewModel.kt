package jafari.movie.presentation.feature.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jafari.movie.domain.errors.DomainError
import jafari.movie.domain.errors.Result
import jafari.movie.domain.models.Movie
import jafari.movie.domain.usecase.movie.MovieUseCases
import jafari.movie.presentation.ui.asErrorUiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel
@Inject
constructor(private val moviesUseCase: MovieUseCases) : ViewModel() {

    private val _refreshState = MutableStateFlow<Result<Unit, DomainError>>(Result.Loading)
    private var refreshJob: Job? = null

    private val movies: StateFlow<List<Movie>> = moviesUseCase.getMovies()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())


    val movieListState: StateFlow<MovieListUiState> =
        combine(movies, _refreshState) { movies, refreshResult ->
            val isRefreshing = refreshResult is Result.Loading

            if (movies.isEmpty()) {
                when (refreshResult) {
                    is Result.Error -> MovieListUiState.LoadFailed(refreshResult.asErrorUiText())
                    is Result.Loading -> MovieListUiState.Loading
                    is Result.Success -> MovieListUiState.Empty
                }
            } else {
                MovieListUiState.Success(
                    movieList = movies,
                    isRefreshing = isRefreshing,
                    error = (refreshResult as? Result.Error)?.asErrorUiText()
                )
            }
        }
        .onStart { refreshMovieList() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MovieListUiState.Loading
        )


    fun onEvent(event: MovieListAction) {
        when (event) {
            MovieListAction.RefreshClicked -> refreshMovieList()
        }
    }

    private fun refreshMovieList() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            _refreshState.value = Result.Loading
            ensureActive()
            _refreshState.value = moviesUseCase.refreshMovies()
        }
    }
}
