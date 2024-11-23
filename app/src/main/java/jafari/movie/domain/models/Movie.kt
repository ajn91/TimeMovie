package jafari.movie.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "popular_movies")
@Serializable
data class Movie(
  @PrimaryKey
  @SerialName("id")
  val id: Int,
  val overview: String,
  @ColumnInfo(name = "poster_url")
  val posterUrl: String,
  @ColumnInfo(name = " release_date")
  val releaseDate: String,
  val title: String,

) {

}
