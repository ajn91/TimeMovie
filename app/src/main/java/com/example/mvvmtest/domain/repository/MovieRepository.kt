package com.example.mvvmtest.domain.repository

import com.example.mvvmtest.data.model.movie.Movie


interface MovieRepository {

    suspend fun getMovies():List<Movie>?
    suspend fun updateMovies():List<Movie>?

}