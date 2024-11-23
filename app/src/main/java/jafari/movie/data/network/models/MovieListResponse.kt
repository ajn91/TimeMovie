package jafari.movie.data.network.models

import jafari.movie.data.local.entities.movie.MovieEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
  @SerialName("results") val movieItems: List<MovieNetwork>,
)
