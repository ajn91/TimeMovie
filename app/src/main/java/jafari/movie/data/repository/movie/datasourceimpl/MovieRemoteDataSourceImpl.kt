package jafari.movie.data.repository.movie.datasourceimpl


import io.filmtime.data.network.adapter.NetworkResponse
import jafari.movie.data.network.TMDBService
import jafari.movie.data.network.models.ApiErrorResponse
import jafari.movie.data.network.models.MovieListResponse
import jafari.movie.data.network.models.MovieNetwork
import jafari.movie.data.repository.movie.datasource.MovieRemoteDatasource
import jafari.movie.di.AppDispatchers.IO
import jafari.movie.di.Dispatcher
import jafari.movie.domain.models.GeneralError
import jafari.movie.domain.models.GeneralErrorThrowable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRemoteDataSourceImpl
@Inject
constructor(
  val tmdbService: TMDBService,
  @Dispatcher(IO) val dispatcher: CoroutineDispatcher,
) : MovieRemoteDatasource {
  override suspend fun getMovies(): List<MovieNetwork> =
    withContext(dispatcher) {
      tmdbService.getPopularMovies().movieItems

    }
}


