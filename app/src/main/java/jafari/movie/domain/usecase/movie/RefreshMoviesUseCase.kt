package jafari.movie.domain.usecase.movie

import jafari.movie.domain.errors.DomainError
import jafari.movie.domain.errors.Result
import jafari.movie.domain.repository.MovieRepository
import javax.inject.Inject

class RefreshMoviesUseCase
@Inject
constructor(val movieRepository: MovieRepository) {
    operator suspend fun invoke(): Result<Unit, DomainError> {
        return movieRepository.refreshMovies()
    }
}
