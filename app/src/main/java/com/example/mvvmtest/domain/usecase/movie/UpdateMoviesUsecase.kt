package com.example.mvvmtest.domain.usecase.movie

import com.example.mvvmtest.data.model.movie.Movie
import com.example.mvvmtest.domain.repository.MovieRepository


class UpdateMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke():List<Movie>? = movieRepository.updateMovies()
}