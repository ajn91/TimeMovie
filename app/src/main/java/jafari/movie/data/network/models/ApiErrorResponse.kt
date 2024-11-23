package jafari.movie.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ApiErrorResponse(
  @SerialName("status_code")
  val statusCode: Int,
  @SerialName("status_message")
  val statusMessage: String,
)
