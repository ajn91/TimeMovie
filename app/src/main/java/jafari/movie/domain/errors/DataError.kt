package jafari.movie.domain.errors

import jafari.movie.data.network.adapter.NetworkResponse
import retrofit2.HttpException
import java.io.IOException

sealed interface DataError : Error {
  sealed interface Network : DataError {
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

  sealed interface Local : DataError {
    data object DiskFull : Local
    data object EmptyList : Local
  }
}

fun NetworkResponse.Error<*>.toDataError(): DataError.Network = when (this) {
    is NetworkResponse.Error.ClientError -> when (code) {
        ErrorCodes.Http.INVALID_API_KEY -> DataError.Network.InvalidApiKey
        ErrorCodes.Http.RESOURCE_NOT_FOUND -> DataError.Network.NotFound
        ErrorCodes.Http.REQUEST_TIMEOUT -> DataError.Network.RequestTimeout
        else -> DataError.Network.Unknown
    }
    is NetworkResponse.Error.ServerError -> when (code) {
        ErrorCodes.Http.SERVICE_UNAVAILABLE -> DataError.Network.ServiceUnavailable
        else -> DataError.Network.ServerError
    }
    is NetworkResponse.Error.RedirectError -> DataError.Network.Unknown
    is NetworkResponse.Error.NetworkError -> DataError.Network.NoInternet
    is NetworkResponse.Error.UnknownError -> DataError.Network.Unknown
    is NetworkResponse.Error.HttpError -> DataError.Network.Unknown // Fallback
}

fun Throwable.toDataErrorType(): DataError = when (this) {

  is IOException -> DataError.Network.NoInternet
  is HttpException -> when (code()) {
    ErrorCodes.Http.INVALID_API_KEY -> DataError.Network.InvalidApiKey
    ErrorCodes.Http.REQUEST_TIMEOUT -> DataError.Network.RequestTimeout
    ErrorCodes.Http.RESOURCE_NOT_FOUND -> DataError.Network.NotFound
    ErrorCodes.Http.INTERNAL_SERVER -> DataError.Network.ServerError
    ErrorCodes.Http.SERVICE_UNAVAILABLE -> DataError.Network.ServiceUnavailable
    else -> DataError.Network.Unknown

  }

  else -> DataError.Network.Unknown
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
