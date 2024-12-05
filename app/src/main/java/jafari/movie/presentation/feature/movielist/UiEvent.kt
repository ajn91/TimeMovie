package jafari.movie.presentation.feature.movielist

import jafari.movie.presentation.ui.UiText

sealed interface UiEvent {
    data class ShowErrorMessage(val error:UiText) : UiEvent
}
