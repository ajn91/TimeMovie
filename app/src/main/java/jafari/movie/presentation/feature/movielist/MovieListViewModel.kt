package jafari.movie.presentation.feature.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jafari.movie.domain.errors.DomainError
import jafari.movie.domain.errors.Result
import jafari.movie.domain.models.Movie
import jafari.movie.domain.usecase.movie.MovieUseCases
import jafari.movie.presentation.ui.UiText
import jafari.movie.presentation.ui.asErrorUiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MovieListEvent {
    data class ShowSnackBar(val message: UiText) : MovieListEvent
}

@HiltViewModel
class MovieListViewModel
@Inject
constructor(private val moviesUseCase: MovieUseCases) : ViewModel() {

    private val _refreshState = MutableStateFlow<Result<Unit, DomainError>>(Result.Loading)
    private var refreshJob: Job? = null

    private val _events = MutableSharedFlow<MovieListEvent>()
    val events = _events.asSharedFlow()

    private val movies: StateFlow<List<Movie>> = moviesUseCase.getMovies()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(StopTimeoutMillis), emptyList())


    val movieListState: StateFlow<MovieListUiState> =
        combine(movies, _refreshState, ::mapToUiState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(StopTimeoutMillis),
                initialValue = MovieListUiState.Loading
            )

    init {
        refreshMovieList()
    }

    fun onEvent(event: MovieListAction) {
        when (event) {
            MovieListAction.RefreshClicked -> refreshMovieList()
        }
    }

    private fun refreshMovieList() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            _refreshState.value = Result.Loading
            val result = moviesUseCase.refreshMovies()
            _refreshState.value = result

            if (movies.value.isNotEmpty() && result is Result.Error) {
                _events.emit(MovieListEvent.ShowSnackBar(result.asErrorUiText()))
            }
        }
    }

    private fun mapToUiState(
        movies: List<Movie>,
        refreshResult: Result<Unit, DomainError>
    ): MovieListUiState {
        if (movies.isNotEmpty()) {
            return MovieListUiState.Success(
                movieList = movies,
                isRefreshing = refreshResult is Result.Loading,
            )
        }

        return when (refreshResult) {
            is Result.Error -> MovieListUiState.LoadFailed(refreshResult.asErrorUiText())
            is Result.Loading -> MovieListUiState.Loading
            is Result.Success -> MovieListUiState.Empty
        }
    }

    companion object {
        private const val StopTimeoutMillis = 5_000L
    }
}
