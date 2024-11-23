package jafari.movie.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieNetwork(
  val id: Int,
  val overview: String,
  @SerialName("poster_path")
  val posterPath: String,
  @SerialName("release_date")
  val releaseDate: String,
  val title: String,

)
