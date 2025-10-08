package jafari.movie.data.repository.movie.datasourceimpl

import io.ktor.client.HttpClient
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import jafari.movie.data.network.adapter.NetworkResponse
import jafari.movie.data.network.models.MovieListResponse
import jafari.movie.data.network.safeRequest
import jafari.movie.data.repository.movie.datasource.MovieRemoteDatasource
import jafari.movie.di.AppDispatchers.IO
import jafari.movie.di.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRemoteDataSourceImpl
@Inject
constructor(
  private val client: HttpClient,
  @Dispatcher(IO) val dispatcher: CoroutineDispatcher,
) : MovieRemoteDatasource {

  override suspend fun getPopularMovies(): NetworkResponse<MovieListResponse, Any> {
  return  withContext(dispatcher) {
       client.safeRequest {
        method = HttpMethod.Get
        url("/movie/popular")
      }
    }
  }
}
