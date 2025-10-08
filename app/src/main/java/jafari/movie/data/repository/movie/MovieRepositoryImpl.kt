package jafari.movie.data.repository.movie


import jafari.movie.data.mapper.asEntity
import jafari.movie.data.mapper.asExternalModel
import jafari.movie.data.network.adapter.NetworkResponse
import jafari.movie.data.repository.movie.datasource.MovieLocalDataSource
import jafari.movie.data.repository.movie.datasource.MovieRemoteDatasource
import jafari.movie.di.AppDispatchers.IO
import jafari.movie.di.Dispatcher
import jafari.movie.domain.errors.DataError
import jafari.movie.domain.errors.Result
import jafari.movie.domain.models.Movie
import jafari.movie.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl
@Inject constructor(
  val movieRemoteDatasource: MovieRemoteDatasource,
  val movieLocalDataSource: MovieLocalDataSource,
  @Dispatcher(IO) val dispatcher: CoroutineDispatcher,
) : MovieRepository {


  override fun getMovies(): Flow<List<Movie>> =
    movieLocalDataSource.getMovies()
      .map { it.map { it.asExternalModel() } }
      .flowOn(Dispatchers.IO)

  override suspend fun refreshMovies(): Result<Unit, DataError> {
    return withContext(dispatcher) {
        when (val movieResult = movieRemoteDatasource.getPopularMovies()) {
            is NetworkResponse.Success -> {
                movieResult.body?.let {
                    movieLocalDataSource.saveMovies(it.movieItems.map { it.asEntity() })
                }
                Result.Success(Unit)
            }
            is NetworkResponse.ApiError -> Result.Error(DataError.Network.UNKNOWN) // Or map the specific error
            is NetworkResponse.NetworkError -> Result.Error(DataError.Network.NO_INTERNET)
            is NetworkResponse.UnknownError -> Result.Error(DataError.Network.UNKNOWN)
        }
    }
  }
}
