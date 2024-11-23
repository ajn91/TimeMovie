package jafari.movie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jafari.movie.data.local.entities.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveMovies(movieItemEntities: List<MovieEntity>)

  @Query("DELETE FROM popular_movies")
  suspend fun deleteAllMovies()

  @Query("SELECT * FROM popular_movies")
  fun getMovies(): Flow<List<MovieEntity>>
}
