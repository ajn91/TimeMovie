package jafari.movie.domain.errors

import jafari.movie.data.network.adapter.NetworkResponse
import retrofit2.HttpException
import java.io.IOException

sealed interface DomainError : Error {
  sealed interface Network : DomainError {
    data object RequestTimeout : Network
    data object InvalidApiKey : Network
    data object NotFound : Network
    data object TooManyRequests : Network
    data object NoInternet : Network
    data object ServerError : Network
    data object ServiceUnavailable : Network
    data object Serialization : Network
    data object Unknown : Network
  }

  sealed interface Local : DomainError {
    data object DiskFull : Local
    data object EmptyList : Local
  }
}

fun NetworkResponse.Error<*>.toDomainError(): DomainError.Network = when (this) {
    is NetworkResponse.Error.ClientError -> when (code) {
        ErrorCodes.Http.INVALID_API_KEY -> DomainError.Network.InvalidApiKey
        ErrorCodes.Http.RESOURCE_NOT_FOUND -> DomainError.Network.NotFound
        ErrorCodes.Http.REQUEST_TIMEOUT -> DomainError.Network.RequestTimeout
        else -> DomainError.Network.Unknown
    }
    is NetworkResponse.Error.ServerError -> when (code) {
        ErrorCodes.Http.SERVICE_UNAVAILABLE -> DomainError.Network.ServiceUnavailable
        else -> DomainError.Network.ServerError
    }
    is NetworkResponse.Error.RedirectError -> DomainError.Network.Unknown
    is NetworkResponse.Error.NetworkError -> DomainError.Network.NoInternet
    is NetworkResponse.Error.UnknownError -> DomainError.Network.Unknown
    is NetworkResponse.Error.HttpError -> DomainError.Network.Unknown // Fallback
}

fun Throwable.toDomainError(): DomainError = when (this) {

  is IOException -> DomainError.Network.NoInternet
  is HttpException -> when (code()) {
    ErrorCodes.Http.INVALID_API_KEY -> DomainError.Network.InvalidApiKey
    ErrorCodes.Http.REQUEST_TIMEOUT -> DomainError.Network.RequestTimeout
    ErrorCodes.Http.RESOURCE_NOT_FOUND -> DomainError.Network.NotFound
    ErrorCodes.Http.INTERNAL_SERVER -> DomainError.Network.ServerError
    ErrorCodes.Http.SERVICE_UNAVAILABLE -> DomainError.Network.ServiceUnavailable
    else -> DomainError.Network.Unknown

  }

  else -> DomainError.Network.Unknown
}

object ErrorCodes {

  object Http {
    const val INVALID_API_KEY = 401
    const val RESOURCE_NOT_FOUND = 404
    const val REQUEST_TIMEOUT = 408
    const val INTERNAL_SERVER = 501
    const val SERVICE_UNAVAILABLE = 503
  }
}
