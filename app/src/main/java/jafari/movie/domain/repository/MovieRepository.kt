package jafari.movie.domain.repository


import jafari.movie.domain.errors.Result
import jafari.movie.domain.errors.DomainError
import jafari.movie.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
  fun getMovies():  Flow<List<Movie>>

  suspend fun refreshMovies(): Result<Unit, DomainError>
}
