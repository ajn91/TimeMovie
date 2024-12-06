package jafari.movie.presentation.feature.movielist.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import jafari.movie.R
import jafari.movie.domain.models.Movie
import jafari.movie.presentation.ui.theme.TransParentWhite


@Composable
fun MovieItemCard(
  item: Movie,
  modifier: Modifier = Modifier,
  onItemClicked: () -> Unit,
) {
  Card(
    onClick = onItemClicked,
    modifier = modifier,
  ) {
    MovieListItem(item)
  }
}


@Composable
fun MovieListItem(
  item: Movie,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .padding(bottom = 8.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {

    Box {
      AsyncImage(
        model = item.posterUrl,
        contentDescription = item.title,
        placeholder = painterResource(R.drawable.ic_movie),
        modifier = Modifier
          .fillMaxSize()
          .aspectRatio(2 / 3f),
        contentScale = ContentScale.Crop,
      )

      MovieYearContainer {
        Text(
          text = item.releaseDate,
          style = MaterialTheme.typography.titleMedium,
          modifier = Modifier
            .align(Alignment.TopStart)
            .padding(horizontal = 8.dp),
        )
      }
    }

    Text(
      text = item.title,
      style = MaterialTheme.typography.titleMedium,
      textAlign = TextAlign.Center,
      maxLines = 1,
      modifier = Modifier
        .padding(horizontal = 8.dp)
        .fillMaxSize(),
    )
  }
}


@Composable
fun MovieYearContainer(content: @Composable () -> Unit) {
  Surface(
    shape = RoundedCornerShape(topStart = 8.dp),
    color = TransParentWhite,
    contentColor = MaterialTheme.colorScheme.surfaceVariant,
  ) {
    content()
  }
}

@Preview(apiLevel = 34)
@Composable
fun MovieListItem() {
  val movie =
    Movie(
      0,
      overview = "test",
      posterUrl = R.drawable.ic_movie.toString(),
      releaseDate = "1992/02/05",
      title = "Avatar 2",
    )

  MovieItemCard (movie){}
}

