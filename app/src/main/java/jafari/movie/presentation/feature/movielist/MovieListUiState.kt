package jafari.movie.presentation.feature.movielist

import jafari.movie.domain.models.Movie
import jafari.movie.presentation.ui.UiText


sealed interface MovieListUiState {
  data object Loading : MovieListUiState
  data class LoadFailed(val uiText: UiText) : MovieListUiState
  data class Success(val movieList: List<Movie>) : MovieListUiState

}
