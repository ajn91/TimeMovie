package com.example.mvvmtest.data.repository.movie.datasource

import com.example.mvvmtest.data.model.movie.MovieList
import retrofit2.Response

interface MovieRemoteDatasource {
   suspend fun getMovies(): Response<MovieList>
}