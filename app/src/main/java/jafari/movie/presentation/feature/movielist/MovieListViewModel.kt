package jafari.movie.presentation.feature.movielist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jafari.movie.domain.errors.DataError
import jafari.movie.domain.errors.Result
import jafari.movie.domain.usecase.movie.MovieUseCases
import jafari.movie.presentation.ui.UiText
import jafari.movie.presentation.ui.asErrorUiText
import jafari.movie.presentation.ui.asUiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel
@Inject
constructor(val moviesUseCase: MovieUseCases) : ViewModel() {

  val movieListStream = moviesUseCase.getMovies()
  var updateStream = MutableStateFlow<Result<Unit, DataError>>(Result.Loading)
  private val _errorMessageFlow = MutableSharedFlow<UiEvent>()
  val uiEventFlow = _errorMessageFlow.asSharedFlow()
  var updateJob: Job? = null

  val movieListState: StateFlow<MovieListUiState> =
    combine(movieListStream, updateStream) { movies, result ->
      when (result) {
        is Result.Error -> {
          val error = result.asErrorUiText()
          if (!movies.isEmpty()) {
            _errorMessageFlow.emit(UiEvent.ShowErrorMessage(error))
            MovieListUiState.Success(movies)
          } else {
            MovieListUiState.LoadFailed(error)
          }
        }

        is Result.Success -> {
          if (!movies.isEmpty()) {
            MovieListUiState.Success(movies)
          } else {
            MovieListUiState.LoadFailed(DataError.Local.EMPTY_LIST.asUiText())

          }
        }

        is Result.Loading -> {
          if (movies.isEmpty())
            MovieListUiState.Loading
          else {
            MovieListUiState.Success(movies)
          }
        }
      }
    }.onStart {
      refreshMovieList()
      Log.d("LOG", "refresh")
    }
      .stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(5_000), MovieListUiState.Loading)

  init {
//    refreshMovieList()
  }
//      .map { result ->
//        when (result) {
//          is Result.Error -> {
//            val error = result.error.asUiText()
//            MovieListUiState.LoadFailed(error)
//          }
//
//          is Result.Success -> {
//            MovieListUiState.Success(result.data)
//          }
//        }
//      }
//      .stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(5_000), MovieListUiState.Loading)


  fun onEvent(event: MovieListEvent) {
    when (event) {
      MovieListEvent.RefreshClicked -> {
        refreshMovieList()
      }

    }
  }

  fun refreshMovieList() {
    updateJob?.cancel()
    updateJob = viewModelScope.launch {
      updateStream.update {
        moviesUseCase.refreshMovies()
      }

    }

  }

//  fun getMovies() {
//    viewModelScope.launch {
//      moviesUseCase.getMovies()
//        .onStart {
//          movieListState.update { state ->
//            state.copy(isLoading = true)
//          }
//        }
//        .onCompletion {
//          movieListState.update { state ->
//            state.copy(isLoading = false)
//          }
//        }
//        .onEach { result ->
//          if (result is Result.Success) {
//            movieListState.update { state ->
//              state.copy(isLoading = false, movieList = result.successValue(), isError = false)
//            }
//          }
//        }.catch { cause ->
//          if (cause is GeneralErrorThrowable) {
//            when (cause.generalError) {
//
//              is GeneralError.ApiError -> {
//                movieListState.update { state ->
//                  state.copy(isError = true, errorMessage = cause.generalError.message ?: "error")
//                }
//              }
//
//              GeneralError.NetworkError -> {
//                movieListState.update { state ->
//                  state.copy(isError = true, errorMessage = "error")
//                }
//              }
//
//              is GeneralError.UnknownError -> {
//                movieListState.update { state ->
//                  state.copy(isError = true, errorMessage = "error")
//                }
//              }
//            }
//          } else {
//            movieListState.update { state ->
//              state.copy(isError = true, errorMessage = "unknown")
//            }
//          }
//        }
//        .collect()
//    }
//  }


}
