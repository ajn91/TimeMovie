package jafari.movie.domain.errors

import retrofit2.HttpException
import java.io.IOException

sealed interface DataError : Error {
  enum class Network : DataError {
    REQUEST_TIMEOUT,
    INVALID_API_KEY,
    NOT_FOUND,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    SERVICE_UNAVAILABLE,
    SERIALIZATION,
    UNKNOWN
  }

  enum class Local : DataError {
    DISK_FULL,
    EMPTY_LIST
  }
}


fun Throwable.toDataErrorType(): DataError = when (this) {

  is IOException -> DataError.Network.NO_INTERNET
  is HttpException -> when (code()) {
    ErrorCodes.Http.INVALID_API_KEY -> DataError.Network.INVALID_API_KEY
    ErrorCodes.Http.REQUEST_TIMEOUT -> DataError.Network.REQUEST_TIMEOUT
    ErrorCodes.Http.RESOURCE_NOT_FOUND -> DataError.Network.NOT_FOUND
    ErrorCodes.Http.INTERNAL_SERVER -> DataError.Network.SERVER_ERROR
    ErrorCodes.Http.SERVICE_UNAVAILABLE -> DataError.Network.SERVICE_UNAVAILABLE
    else -> DataError.Network.UNKNOWN
  }

  else -> DataError.Network.UNKNOWN
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
