package jafari.movie.presentation.feature.movielist

import jafari.movie.presentation.ui.UiText

sealed interface MovieListEvent {
    data object RefreshClicked : MovieListEvent
    data object ClearErrorMessage : MovieListEvent
}
