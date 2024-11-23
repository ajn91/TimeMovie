package jafari.movie.presentation.feature.movielist

sealed interface MovieListEvent {
    data object RefreshClicked : MovieListEvent
}
