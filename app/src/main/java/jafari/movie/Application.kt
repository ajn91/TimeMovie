package jafari.movie

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
  override fun onCreate() {
    super.onCreate()
    Log.d("LOG", "created app")

  }
}
