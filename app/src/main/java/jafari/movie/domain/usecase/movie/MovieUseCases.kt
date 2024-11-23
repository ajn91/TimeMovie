package jafari.movie.domain.usecase.movie

import javax.inject.Inject

data class MovieUseCases
  @Inject
  constructor(
    val getMovies: GetMoviesUseCase,
    val refreshMovies: RefreshMoviesUseCase,
  )
