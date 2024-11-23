package jafari.movie.domain.usecase.movie


import jafari.movie.data.local.entities.movie.MovieEntity
import jafari.movie.domain.models.Movie
import jafari.movie.domain.repository.MovieRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException
import javax.inject.Inject

private const val RETRY_TIME_IN_MILLIS = 10_000L
//
//class GetMoviesUseCase2
//  @Inject
//  constructor(private val movieRepository: MovieRepository) {
//    operator fun invoke(): Flow<Result<List<Movie>>> =
//      movieRepository.getMovies()
//        .map {
//          Result.success(it)
//        }
//        .retryWhen { cause, _ ->
//          if (cause is IOException) {
//            emit(Result.failure(cause))
//
//            delay(RETRY_TIME_IN_MILLIS)
//            true
//          } else {
//            false
//          }
//        }
//        .catch {
//          // for other than IOException but it will stop collecting Flow
//          emit(Result.failure<com.plcoding.cleanerrorhandling.domain.Result<List<Movie>, DataError>>(it).isFailure) // also catch does re-throw CancellationException
//        }
//  }
