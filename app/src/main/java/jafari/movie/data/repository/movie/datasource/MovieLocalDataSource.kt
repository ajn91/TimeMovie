package jafari.movie.data.repository.movie.datasource

import jafari.movie.data.local.entities.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
  fun getMovies(): Flow<List<MovieEntity>>

  suspend fun saveMovies(movieItemEntities: List<MovieEntity>)

  suspend fun clearAll()
}
