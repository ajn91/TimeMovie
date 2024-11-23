package jafari.movie.data.di


import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import jafari.movie.data.repository.movie.MovieRepositoryImpl
import jafari.movie.data.repository.movie.datasource.MovieLocalDataSource
import jafari.movie.data.repository.movie.datasource.MovieRemoteDatasource
import jafari.movie.data.repository.movie.datasourceimpl.MovieLocalDataSourceImpl
import jafari.movie.data.repository.movie.datasourceimpl.MovieRemoteDataSourceImpl
import jafari.movie.domain.repository.MovieRepository
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class MovieModule {

  @Binds
  abstract fun bindLocalDataSource(movieLocalDataSource: MovieLocalDataSourceImpl): MovieLocalDataSource

  @Binds
  abstract fun bindRemoteDataSource(movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieRemoteDatasource

  @Binds
 abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}
