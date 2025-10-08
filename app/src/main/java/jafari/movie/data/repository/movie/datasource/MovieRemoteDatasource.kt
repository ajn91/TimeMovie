package jafari.movie.data.repository.movie.datasource

import jafari.movie.data.network.adapter.NetworkResponse
import jafari.movie.data.network.models.MovieListResponse

interface MovieRemoteDatasource {
  suspend fun getPopularMovies(): NetworkResponse<MovieListResponse, Any>
}
