package jafari.movie.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
  @SerialName("results") val movieItems: List<MovieNetwork>,
)
