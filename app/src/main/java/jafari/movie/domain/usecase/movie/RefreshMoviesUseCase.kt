package jafari.movie.domain.usecase.movie

import jafari.movie.domain.errors.DataError
import jafari.movie.domain.errors.Result
import jafari.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RefreshMoviesUseCase
  @Inject
  constructor(val movieRepository: MovieRepository) {
     operator suspend fun invoke(): Result<Unit, DataError>{
    return  movieRepository.refreshMovies()
    }
  }
