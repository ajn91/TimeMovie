package jafari.movie.presentation.feature.movielist

import jafari.movie.domain.models.Movie
import jafari.movie.presentation.ui.UiText


sealed interface MovieListUiState {
  val isRefreshing: Boolean
  val error: UiText?

  data object Loading : MovieListUiState {
    override val isRefreshing: Boolean = true
    override val error: UiText? = null
  }

  data class Success(
    val movieList: List<Movie>,
    override val isRefreshing: Boolean = false,
    override val error: UiText? = null
  ) : MovieListUiState
  
    data object Empty : MovieListUiState {
        override val isRefreshing: Boolean = false
        override val error: UiText? = null
    }

    data class LoadFailed(override val error: UiText) : MovieListUiState {
        override val isRefreshing: Boolean = false
    }
}
