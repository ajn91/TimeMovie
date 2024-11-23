package jafari.movie.data.network

import io.filmtime.data.network.adapter.NetworkResponse
import jafari.movie.data.network.models.ApiErrorResponse
import jafari.movie.data.network.models.MovieListResponse
import retrofit2.http.GET

interface TMDBService {
  @GET("movie/popular")
  suspend fun getPopularMovies(): MovieListResponse
}
