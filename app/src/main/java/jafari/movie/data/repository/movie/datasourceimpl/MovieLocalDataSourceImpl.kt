package jafari.movie.data.repository.movie.datasourceimpl


import jafari.movie.data.local.dao.MovieDao
import jafari.movie.di.AppDispatchers.IO
import jafari.movie.di.Dispatcher
import jafari.movie.data.local.entities.movie.MovieEntity
import jafari.movie.data.repository.movie.datasource.MovieLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieLocalDataSourceImpl
@Inject
constructor(val movieDao: MovieDao, @Dispatcher(IO) val dispatcher: CoroutineDispatcher) :
  MovieLocalDataSource {
  override fun getMovies(): Flow<List<MovieEntity>> {
    return movieDao.getMovies().flowOn(dispatcher)
  }

  override suspend fun saveMovies(movieItemEntities: List<MovieEntity>) {
    withContext(dispatcher) {
      movieDao.saveMovies(movieItemEntities)
    }
  }

  override suspend fun clearAll() {
    withContext(dispatcher) {
      movieDao.deleteAllMovies()
    }
  }
}
