package jafari.movie.presentation.ui


import jafari.movie.R
import jafari.movie.domain.errors.DomainError
import jafari.movie.domain.errors.Result

fun DomainError.asUiText(): UiText {
  return when (this) {
    DomainError.Network.RequestTimeout -> UiText.StringResource(
      R.string.the_request_timed_out
    )

    DomainError.Network.TooManyRequests -> UiText.StringResource(
      R.string.youve_hit_your_rate_limit
    )

    DomainError.Network.NoInternet -> UiText.StringResource(
      R.string.no_internet
    )

    DomainError.Network.ServerError -> UiText.StringResource(
      R.string.server_error
    )

    DomainError.Network.Serialization -> UiText.StringResource(
      R.string.error_serialization
    )

    DomainError.Network.Unknown -> UiText.StringResource(
      R.string.unknown_error
    )

    DomainError.Local.DiskFull -> UiText.StringResource(
      R.string.error_disk_full
    )

    DomainError.Network.ServiceUnavailable -> UiText.StringResource(
      R.string.server_error
    )

    DomainError.Network.NotFound -> UiText.StringResource(
      R.string.not_found
    )

    DomainError.Network.InvalidApiKey -> UiText.StringResource(
      R.string.invalid_api_key
    )

    DomainError.Local.EmptyList -> UiText.StringResource(
      R.string.empty_list
    )
  }
}

fun Result.Error<*, DomainError>.asErrorUiText(): UiText {
  return error.asUiText()
}
