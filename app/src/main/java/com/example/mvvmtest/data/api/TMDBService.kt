package com.example.mvvmtest.data.api


import com.example.mvvmtest.data.model.movie.MovieList
import retrofit2.Response
import retrofit2.http.GET

interface TMDBService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
    ): Response<MovieList>




}