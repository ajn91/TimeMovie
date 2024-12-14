package jafari.movie.presentation.ui


import jafari.movie.R
import jafari.movie.domain.errors.DataError
import jafari.movie.domain.errors.Result

fun DataError.asUiText(): UiText {
  return when (this) {
    DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
      R.string.the_request_timed_out
    )

    DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
      R.string.youve_hit_your_rate_limit
    )

    DataError.Network.NO_INTERNET -> UiText.StringResource(
      R.string.no_internet
    )

    DataError.Network.SERVER_ERROR -> UiText.StringResource(
      R.string.server_error
    )

    DataError.Network.SERIALIZATION -> UiText.StringResource(
      R.string.error_serialization
    )

    DataError.Network.UNKNOWN -> UiText.StringResource(
      R.string.unknown_error
    )

    DataError.Local.DISK_FULL -> UiText.StringResource(
      R.string.error_disk_full
    )

    DataError.Network.SERVICE_UNAVAILABLE -> UiText.StringResource(
      R.string.server_error
    )

    DataError.Network.NOT_FOUND -> UiText.StringResource(
      R.string.not_found
    )

    DataError.Network.INVALID_API_KEY -> UiText.StringResource(
      R.string.invalid_api_key
    )

    DataError.Local.EMPTY_LIST -> UiText.StringResource(
      R.string.empty_list
    )
  }
}

fun Result.Error<*, DataError>.asErrorUiText(): UiText {
  return error.asUiText()
}
