package jafari.movie.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jafari.movie.data.local.dao.MovieDao
import jafari.movie.data.local.db.TMDBDatabase
import jafari.movie.utilities.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

  @Provides
  @Singleton
  fun provideMovieDataBase(
    @ApplicationContext context: Context,
  ): TMDBDatabase =
    Room.databaseBuilder(
      context,
      TMDBDatabase::class.java,
      DATABASE_NAME,
    ).build()

  @Provides
  fun provideMovieDao(db: TMDBDatabase): MovieDao {
    return db.movieDao()
  }
}
