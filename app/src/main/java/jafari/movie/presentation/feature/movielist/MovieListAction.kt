package jafari.movie.presentation.feature.movielist

sealed interface MovieListAction {
    data object RefreshClicked : MovieListAction }
