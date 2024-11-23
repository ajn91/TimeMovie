package jafari.movie.data.repository.movie.datasource

import jafari.movie.data.local.entities.movie.MovieEntity
import jafari.movie.data.network.models.MovieNetwork
import jafari.movie.domain.models.GeneralError


interface MovieRemoteDatasource {
  suspend fun getMovies(): List<MovieNetwork>
}
