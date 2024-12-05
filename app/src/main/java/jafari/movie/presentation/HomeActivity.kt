package jafari.movie.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import jafari.movie.presentation.navigation.MovieNavigation
import jafari.movie.presentation.ui.theme.MovieTheme

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    val splashScreen = installSplashScreen()
    super.onCreate(savedInstanceState)

    splashScreen.setKeepOnScreenCondition {
      false
    }


    enableEdgeToEdge()
    setContent {
      MovieTheme {
        Scaffold(modifier = Modifier.fillMaxWidth()) { innerPadding ->
          MovieNavigation(
            modifier = Modifier.padding(innerPadding)
              .consumeWindowInsets(innerPadding),
          )
        }
      }
    }
  }
}
