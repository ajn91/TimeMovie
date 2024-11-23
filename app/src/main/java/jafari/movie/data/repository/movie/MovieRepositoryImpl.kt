package jafari.movie.data.repository.movie


import jafari.movie.data.mapper.asEntity
import jafari.movie.data.mapper.asExternalModel
import jafari.movie.data.repository.movie.datasource.MovieLocalDataSource
import jafari.movie.data.repository.movie.datasource.MovieRemoteDatasource
import jafari.movie.di.AppDispatchers.IO
import jafari.movie.di.Dispatcher
import jafari.movie.domain.errors.DataError
import jafari.movie.domain.errors.Result
import jafari.movie.domain.errors.toDataErrorType
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
//      .map {
//        Result.Success<List<Movie>, DataError>(it.map { it.asExternalModel() }) as Result<List<Movie>, DataError>
//      }.onEach { rockets ->
//        if (rockets is Result.Success && rockets.data.isEmpty()) {
//          refreshMovies()
//        }


//      .catch { exception ->
//        val error = exception.toDataErrorType()
////        val error: Result<List<Movie>, DataError> = when (exception) {
////          is HttpException -> {
////            when (exception.code()) {
////              408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
////              413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
////              else -> Result.Error(DataError.Network.UNKNOWN)
////            }
////
////          }
////
////          else -> {
////            Result.Error(DataError.Network.UNKNOWN)
////          }
////        }
//        emit(Result.Error(error))
//
//      }


  override suspend fun refreshMovies(): Result<Unit, DataError> {
    return withContext(dispatcher) {
      try {
        val movieResult = movieRemoteDatasource.getMovies()
        movieLocalDataSource.saveMovies(movieResult.map { it.asEntity() })
        Result.Success(Unit)
      } catch (exception: Throwable) {
        Result.Error(exception.toDataErrorType())
      }
    }

  }


}
