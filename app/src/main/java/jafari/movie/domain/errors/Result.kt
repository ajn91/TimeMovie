package jafari.movie.domain.errors

sealed interface Result<out D, out E : DomainError> {
  data class Success<out D, out E : DomainError>(val data: D) : Result<D, E>

  data class Error<out D, out E : DomainError>(
    val error: E,
  ) : Result<D, E>

  data object Loading : Result<Nothing, Nothing>
}
