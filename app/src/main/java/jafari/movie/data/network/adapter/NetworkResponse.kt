package jafari.movie.data.network.adapter

import java.io.IOException

sealed class NetworkResponse<out S : Any, out E : Any> {

  data class Success<S : Any>(val body: S?) : NetworkResponse<S, Nothing>()

  sealed class Error<E: Any> : NetworkResponse<Nothing, E>() {
    data class HttpError<E : Any>(val body: E, val code: Int) : Error<E>()
    data class ClientError<E : Any>(val body: E, val code: Int) : Error<E>()
    data class ServerError<E : Any>(val body: E, val code: Int) : Error<E>()
    data class RedirectError<E : Any>(val body: E, val code: Int) : Error<E>()
    data class NetworkError(val error: IOException) : Error<Nothing>()
    data class UnknownError(val error: Throwable) : Error<Nothing>()
  }
}
