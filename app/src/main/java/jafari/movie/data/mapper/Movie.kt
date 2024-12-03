package jafari.movie.data.mapper

import jafari.movie.data.local.entities.movie.MovieEntity
import jafari.movie.data.network.models.MovieNetwork
import jafari.movie.domain.models.Movie

internal const val TMDB_BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"


fun MovieNetwork.asEntity() = MovieEntity(
  id = id,
  overview = overview,
  posterUrl = TMDB_BASE_IMAGE_URL.plus(posterPath),
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
