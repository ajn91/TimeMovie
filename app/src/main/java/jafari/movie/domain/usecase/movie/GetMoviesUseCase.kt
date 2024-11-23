package jafari.movie.domain.usecase.movie



import jafari.movie.domain.errors.DataError
import jafari.movie.domain.models.Movie
import jafari.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import jafari.movie.domain.errors.Result
import javax.inject.Inject

private const val RETRY_TIME_IN_MILLIS = 10_000L

class GetMoviesUseCase
@Inject
constructor(private val movieRepository: MovieRepository) {
  operator fun invoke(): Flow<List<Movie>> =
    movieRepository.getMovies()

}

