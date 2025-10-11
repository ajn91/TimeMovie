package jafari.movie.presentation.feature.movielist

import jafari.movie.domain.models.Movie
import jafari.movie.presentation.ui.UiText


sealed interface MovieListUiState {
  val isRefreshing: Boolean

  data object Loading : MovieListUiState {
    override val isRefreshing: Boolean = true
  }

  data class Success(
    val movieList: List<Movie>,
    override val isRefreshing: Boolean = false,
  ) : MovieListUiState

    data object Empty : MovieListUiState {
        override val isRefreshing: Boolean = false
    }

    data class LoadFailed(val error: UiText) : MovieListUiState {
        override val isRefreshing: Boolean = false
    }
}
