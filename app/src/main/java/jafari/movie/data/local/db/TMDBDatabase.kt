package jafari.movie.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import jafari.movie.data.local.dao.MovieDao
import jafari.movie.data.local.entities.movie.MovieEntity

@Database(
  entities = [MovieEntity::class],
  version = 1,
  exportSchema = false,
)
abstract class TMDBDatabase : RoomDatabase() {
  abstract fun movieDao(): MovieDao
}
