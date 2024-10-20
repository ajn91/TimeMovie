package com.example.mvvmtest.data.repository.movie.datasource

import com.example.mvvmtest.data.model.movie.Movie


interface MovieLocalDataSource {
  suspend fun getMoviesFromDB():List<Movie>
  suspend fun saveMoviesToDB(movies:List<Movie>)
  suspend fun clearAll()
}