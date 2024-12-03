package jafari.movie.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import jafari.movie.presentation.feature.movielist.MovieListScreen
import jafari.movie.presentation.feature.start.StartScreen
import kotlinx.serialization.Serializable

object Screen {
  @Serializable
  object Start

  @Serializable
  object Movies

  @Serializable
  data class Movie(val id: String)
}


@Composable
fun MovieNavigation(modifier: Modifier = Modifier) {
  val navController = rememberNavController()
  NavHost(
    navController = navController,
    startDestination = Screen.Start,
  ) {
    composable<Screen.Start> {
      StartScreen(modifier = modifier) {
        navController.navigate(Screen.Movies)
      }

    }
    composable<Screen.Movies> {
      MovieListScreen(modifier = modifier)
    }
    composable<Screen.Movie> {
      val args = it.toRoute<Screen.Movie>()

    }
  }
}
