package jafari.movie.data.mapper

import jafari.movie.data.local.entities.movie.MovieEntity
import jafari.movie.data.network.models.MovieNetwork
import jafari.movie.domain.models.Movie

fun MovieNetwork.asEntity() = MovieEntity(
  id = id,
  overview = overview,
  posterUrl = "https://image.tmdb.org/t/p/w500$posterPath",
  releaseDate = releaseDate,
  title = title,
)


fun MovieEntity.asExternalModel() = Movie(
  id = id,
  overview = overview,
  posterUrl =posterUrl,
  releaseDate = releaseDate,
  title = title,
)
