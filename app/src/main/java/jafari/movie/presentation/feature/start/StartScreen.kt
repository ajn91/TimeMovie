package jafari.movie.presentation.feature.start

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import jafari.movie.presentation.feature.movielist.MovieListViewModel


@Composable
fun StartScreen(viewModel: MovieListViewModel = hiltViewModel(), modifier: Modifier = Modifier,nextButtonClicked: () -> Unit) {
  Box(modifier = modifier) {
    Button(
      onClick = {
        nextButtonClicked()
      },
    ) {
      Text(text = "click")
    }
  }
}
