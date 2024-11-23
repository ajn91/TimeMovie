package jafari.movie.domain.repository


import jafari.movie.domain.errors.Result
import jafari.movie.domain.errors.DataError
import jafari.movie.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
  fun getMovies():  Flow<List<Movie>>

  suspend fun refreshMovies(): Result<Unit, DataError>
}
