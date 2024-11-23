package jafari.movie.presentation.feature.movielist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import jafari.movie.domain.models.Movie
import jafari.movie.presentation.ui.theme.TransParentWhite


@Composable
fun MovieListItem(
  item: Movie,
  onItemClicked: (Movie) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier =
      modifier
        .clickable { onItemClicked(item) }
        .padding(top = 12.dp, bottom = 12.dp),
  ) {
    key(item.id) {

    }
    MovieImageContainer {
      Box {
        val painter =
          rememberAsyncImagePainter(
            model =
              ImageRequest.Builder(LocalContext.current)
                .data(item.posterUrl)
                .crossfade(true)
                .build(),
          )
        Image(
          painter = painter,
          contentDescription = null,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize(),
        )

        if (painter.state.collectAsState().value is AsyncImagePainter.State.Loading) {
          CircularProgressIndicator(
            modifier =
              Modifier
                .align(Alignment.Center)
                .size(80.dp),
            color = MaterialTheme.colorScheme.tertiary,
          )
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_movie),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(36.dp)
//                            .align(Alignment.Center),
//                    )
        }
        MovieYearContainer {
          Text(
            text = item.releaseDate,
            style = MaterialTheme.typography.titleMedium,
            modifier =
              Modifier
                .align(Alignment.TopStart),
          )
        }
      }
    }
    Spacer(Modifier.height(8.dp))
    Text(text = item.title, style = MaterialTheme.typography.titleLarge)
  }
}

@Composable
fun MovieImageContainer(content: @Composable () -> Unit) {
  Surface(
    modifier = Modifier.size(128.dp, 128.dp),
    shape = RoundedCornerShape(4.dp),
  ) {
    content()
  }
}

@Composable
fun MovieYearContainer(content: @Composable () -> Unit) {
  Surface(
    shape = RoundedCornerShape(4.dp),
    color = TransParentWhite,
    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
  ) {
    content()
  }
}

@Preview
@Composable
fun MovieListItem() {
  val movie =
    Movie(
      0,
      overview = "",
      posterUrl = "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg",
      releaseDate = "1992/02/05",
      title = "Avatar 2",
    )
  MovieListItem(movie, onItemClicked = {})
}
