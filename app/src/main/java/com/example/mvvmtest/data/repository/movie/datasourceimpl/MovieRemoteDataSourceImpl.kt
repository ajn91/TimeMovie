package com.example.mvvmtest.data.repository.movie.datasourceimpl


import com.example.mvvmtest.data.api.TMDBService
import com.example.mvvmtest.data.model.movie.MovieList
import com.example.mvvmtest.data.repository.movie.datasource.MovieRemoteDatasource
import retrofit2.Response

class MovieRemoteDataSourceImpl(
    private val tmdbService: TMDBService,
    private val apiKey:String
): MovieRemoteDatasource {
    override suspend fun getMovies(): Response<MovieList> = tmdbService.getPopularMovies()

}

